package com.nami.dfs.ViewModel;


import com.nami.dfs.Model.Node;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FileBlockWithNodeInformation {
    private String blockName;
    private String blockMD5;
    private double blockSize;
    private ArrayList<Node> belongedNodes;

    public FileBlockWithNodeInformation() {
    }

    public FileBlockWithNodeInformation(String blockName, String blockMD5, double blockSize, ArrayList<Node> belongedNodes) {
        this.blockName = blockName;
        this.blockMD5 = blockMD5;
        this.blockSize = blockSize;
        this.belongedNodes = belongedNodes;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockMD5() {
        return blockMD5;
    }

    public void setBlockMD5(String blockMD5) {
        this.blockMD5 = blockMD5;
    }

    public double getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(double blockSize) {
        this.blockSize = blockSize;
    }

    public ArrayList<Node> getBelongedNodes() {
        return belongedNodes;
    }

    public void setBelongedNodes(ArrayList<Node> belongedNodes) {
        this.belongedNodes = belongedNodes;
    }
}