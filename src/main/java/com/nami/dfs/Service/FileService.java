package com.nami.dfs.Service;

import com.nami.dfs.Model.FileBlock;
import com.nami.dfs.Model.FileStructure;
import com.nami.dfs.Model.Node;
import com.nami.dfs.ViewModel.FileBlockWithNodeInformation;
import com.nami.dfs.ViewModel.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {
    private FileListService fileListService;
    private NodesService nodesService;

    @Autowired
    public FileService(FileListService fileListService, NodesService nodesService) {
        this.fileListService = fileListService;
        this.nodesService = nodesService;
    }

    public FileDetail getFileDetail(String fileName) throws IOException {
        FileDetail fileDetail = new FileDetail();
        FileStructure fileStructure = fileListService.findFileByFileName(fileName);
        fileDetail.setFileName(fileStructure.getFileName());
        fileDetail.setFileSize(fileStructure.getFileSize());
        fileDetail.setFileStatus(getFileStatus(fileStructure));
        fileDetail.setFileBlockWithNodeInformation(getInformation(fileStructure));
        return fileDetail;
    }

    public ArrayList<FileBlockWithNodeInformation> getInformation(FileStructure fileStructure) throws IOException {
        ArrayList<FileBlockWithNodeInformation> fileBlockWithNodeInformations = new ArrayList<>();
        ArrayList<FileBlock> fileBlocks = fileStructure.getBlockList();
        for (FileBlock fileBlock : fileBlocks) {
            FileBlockWithNodeInformation fileBlockWithNodeInformation = new FileBlockWithNodeInformation();
            ArrayList<Node> nodes = new ArrayList<>();
            ArrayList<String> ips = fileBlock.getBelongedNodes();
            for (String ip : ips) {
                Node node = nodesService.getNodeByIp(ip);
                nodes.add(node);
            }

            fileBlockWithNodeInformation.setBelongedNodes(nodes);
            fileBlockWithNodeInformation.setBlockMD5(fileBlock.getBlockMD5());
            fileBlockWithNodeInformation.setBlockName(fileBlock.getBlockName());
            fileBlockWithNodeInformation.setBlockSize(fileBlock.getBlockSize());
            fileBlockWithNodeInformations.add(fileBlockWithNodeInformation);
        }
        return fileBlockWithNodeInformations;
    }

    public boolean getFileStatus(FileStructure fileStructure) {
        ArrayList<String> forceStopNodes = nodesService.getForceStopNodes();
        if (forceStopNodes == null) {
            return true;
        }
        ArrayList<FileBlock> fileBlocks = fileStructure.getBlockList();
        for (FileBlock fileBlock : fileBlocks) {
            ArrayList<String> nodes = fileBlock.getBelongedNodes();
            int nodeDisableCount = 0;
            for (String node : nodes) {
                if (forceStopNodes.contains(node)) {
                    nodeDisableCount++;
                }
            }
            if (nodeDisableCount == nodes.size()) {
                return false;
            }
        }
        return true;
    }
}
