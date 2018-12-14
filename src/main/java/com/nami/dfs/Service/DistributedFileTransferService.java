package com.nami.dfs.Service;

import com.nami.dfs.FileUtil;
import com.nami.dfs.Model.FileBlock;
import com.nami.dfs.Model.FileStructure;
import com.nami.dfs.Model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


@Service
public class DistributedFileTransferService {

    @Value("${nodes.blockSize}")
    private int blockSize;

    @Value("${nodes.rootPath}")
    private String path;

    @Value("${nodes.backUpNumber}")
    private int backUpNumber;

    private FileListService fileListService;
    private NodesService nodesService;
    private FileChannelService fileChannelService;

    @Autowired
    public DistributedFileTransferService(NodesService nodesService, FileChannelService fileChannelService, FileListService fileListService) {
        this.nodesService = nodesService;
        this.fileChannelService = fileChannelService;
        this.fileListService = fileListService;
    }

    public ArrayList<FileStructure> deleteFile(String fileName) throws IOException {
        FileStructure fileStructure = fileListService.findFileByFileName(fileName);
        if (fileStructure != null) {
            ArrayList<FileBlock> fileBlocks = fileStructure.getBlockList();
            for (FileBlock fileBlock : fileBlocks) {
                ArrayList<String> nodes = fileBlock.getBelongedNodes();
                for (String node : nodes) {
                    String ip = node;
                    String blockName = fileBlock.getBlockName();
                    fileChannelService.deleteFile(blockName, ip);
                }
            }
            return fileListService.deleteFileByFileName(fileName);
        }
        return fileListService.getFileList();
    }

    public ArrayList<FileStructure> uploadFile(String fileName) throws IOException {
        ArrayList<FileBlock> fileBlockArrayList = new ArrayList<>();
        try {
            FileUtil fileUtil = new FileUtil();
            String filePath = path + fileName.replace(".", "_") + "/";
            File fp = new File(filePath);
            fp.mkdirs();

            // if system contains this same name file, then remove the file firstly.
            deleteFile(fileName);

            ArrayList<String> partName = fileUtil.splitBySize(path + fileName, blockSize * 1024, filePath);
            for (String aPartName : partName) {
                this.uploadFileToRemoteNodes(fileName, aPartName,fileBlockArrayList);
            }

            File file = new File(path + fileName);
            FileStructure fileStructure = new FileStructure();
            fileStructure.setFileName(fileName);
            fileStructure.setFileStatus(true);
            fileStructure.setFileSize(file.length() / 1024);
            fileStructure.setBlockList(fileBlockArrayList);
            fileListService.addFile(fileStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileListService.getFileList();
    }

    public ArrayList<FileStructure> uploadFileByFileStream(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File tmpFile = new File("D:\\" + fileName);
        file.transferTo(tmpFile);
        File desFile = new File(path + fileName);
        copyFileUsingFileChannels(tmpFile, desFile);
        tmpFile.delete();
        return uploadFile(fileName);
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    private void uploadFileToRemoteNodes(String fileName, String partName,ArrayList<FileBlock> fileBlockArrayList) throws IOException {
        ArrayList<Node> nodes = nodesService.sortNodesByStorage();
        String partFilePath = path + fileName.replace(".", "_") + "/" + partName;
        ArrayList<String> belongedNodes = new ArrayList<>();
        for (int i = 0; i < backUpNumber; i++) {
            String ip = nodes.get(i).getNodeIP();
            belongedNodes.add(ip);
            fileChannelService.putFile(partFilePath, ip);
        }

        // record file's belonged nodes
        File file = new File(partFilePath);
        FileBlock fileBlock = new FileBlock();
        fileBlock.setBlockName(partName);
        fileBlock.setBelongedNodes(belongedNodes);
        fileBlock.setBlockMD5(MD5Service.getMd5ByFile(file));
        fileBlock.setBlockSize(file.length() / 1024);
        fileBlockArrayList.add(fileBlock);
    }

    public void downloadFile(String fileName) throws IOException {
        ArrayList<FileStructure> fileStructures = new ArrayList<>();
        FileUtil fileUtil = new FileUtil();
        String dirPath = path + fileName.replace(".", "_");
        fileUtil.mergePartFiles(dirPath, ".part", blockSize * 1024,
                dirPath + "/" + fileName);
    }
}