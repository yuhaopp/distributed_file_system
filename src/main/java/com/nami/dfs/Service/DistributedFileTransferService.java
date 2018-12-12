package com.nami.dfs.Service;

import com.nami.dfs.FileUtil;
import com.nami.dfs.Model.FileBlock;
import com.nami.dfs.Model.FileStructure;
import com.nami.dfs.Model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


@Service
public class DistributedFileTransferService {

    @Value("${nodes.blockSize}")
    private int blockSize;

    @Value("${nodes.rootPath}")
    private String path;

    @Value("${nodes.backUpNumber}")
    private int backUpNumber;

    private ArrayList<FileBlock> fileBlockArrayList = new ArrayList<>();
    private FileListService fileListService = new FileListService();

    private NodesService nodesService;
    private FileChannelService fileChannelService;

    @Autowired
    public DistributedFileTransferService(NodesService nodesService, FileChannelService fileChannelService) {
        this.nodesService = nodesService;
        this.fileChannelService = fileChannelService;
    }

    public void uploadFile(String fileName) {
        try {
            FileUtil fileUtil = new FileUtil();
            String filePath = path + fileName.replace(".", "_") + "/";
            File fp = new File(filePath);
            fp.mkdirs();

            ArrayList<String> partName = fileUtil.splitBySize(path + fileName, blockSize * 1024, filePath);
            for (String aPartName : partName) {
                this.uploadFileToRemoteNodes(fileName, aPartName);
            }

            File file = new File(path + fileName);
            FileStructure fileStructure = new FileStructure();
            fileStructure.setFileName(fileName);
            fileStructure.setFileStatus(true);
            fileStructure.setFileSize(file.length() / 1024);
            fileStructure.setBlockList(fileBlockArrayList);
            fileListService.updateFile(fileStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadFileToRemoteNodes(String fileName, String partName) throws IOException {
        ArrayList<Node> nodes = nodesService.sortNodesByStorage();
        for (int i = 0; i < backUpNumber; i++) {
            String ip = nodes.get(i).getNodeIP();
            String partFilePath = path + fileName.replace(".", "_") + "/" + partName;
            fileChannelService.putFile(partFilePath, ip);

            // record file's belonged nodes
            File file = new File(partFilePath);
            FileBlock fileBlock = new FileBlock();
            fileBlock.setBlockName(partName);
            fileBlock.setBelongedNode(nodes.get(i));
            fileBlock.setBlockMD5(MD5Service.getMd5ByFile(file));
            fileBlock.setBlockSize(file.length() / 1024);
            fileBlockArrayList.add(fileBlock);
        }
    }

    public void downloadFile(String fileName) throws IOException {
        FileUtil fileUtil = new FileUtil();
        String dirPath = path + fileName.replace(".", "_");
        fileUtil.mergePartFiles(dirPath, ".part", blockSize * 1024,
                dirPath + "/" + fileName);
    }
}