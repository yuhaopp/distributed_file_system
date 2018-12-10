package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.nami.dfs.Model.File;
import com.nami.dfs.Model.Node;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
public class DistributedFileTransferService {
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
