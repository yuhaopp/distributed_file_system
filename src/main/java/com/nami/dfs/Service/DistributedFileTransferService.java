package com.nami.dfs.Service;

import com.nami.dfs.Model.File;
import com.nami.dfs.Model.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DistributedFileTransferService {
    public DistributedFileTransferService() {

    }

    public File getFile() {
        return file;
    }

    public void setFile() {
        this.file = file;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    File file = new File();
    ArrayList<Node> nodes = new ArrayList<>();


    public void uploadFile(String fileName) {


    }

}
