package com.nami.dfs.Model;

import org.springframework.stereotype.Component;

@Component
public class FileBlock {

    private String blockName;
    private String blockMD5;
    private double blockSize;
    private Node belongedNode;

    public FileBlock() {
    }

    public FileBlock(String blockName, String blockMD5, double blockSize, Node belongedNode) {
        this.blockName = blockName;
        this.blockMD5 = blockMD5;
        this.blockSize = blockSize;
        this.belongedNode = belongedNode;
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

    public Node getBelongedNode() {
        return belongedNode;
    }

    public void setBelongedNode(Node belongedNode) {
        this.belongedNode = belongedNode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
