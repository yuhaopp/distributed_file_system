package com.nami.dfs.Model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class FileBlock implements Serializable {

    private String blockName;
    private String blockMD5;
    private double blockSize;
    private ArrayList<String> belongedNodes;

    public FileBlock() {
    }

    public FileBlock(String blockName, String blockMD5, double blockSize, ArrayList<String> belongedNodes) {
        this.blockName = blockName;
        this.blockMD5 = blockMD5;
        this.blockSize = blockSize;
        this.belongedNodes = belongedNodes;
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

    public ArrayList<String> getBelongedNodes() {
        return belongedNodes;
    }

    public void setBelongedNodes(ArrayList<String> belongedNodes) {
        this.belongedNodes = belongedNodes;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
