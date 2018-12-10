package com.nami.dfs.Model;

import java.util.ArrayList;

public class File {
    private String fileName;
    private double fileSize;
    private String fileStatus;
    private ArrayList<FileBlock> BlockList;

    public File() {
    }

    public File(String fileName, double fileSize, String fileStatus, ArrayList<FileBlock> blockList) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileStatus = fileStatus;
        BlockList = blockList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public ArrayList<FileBlock> getBlockList() {
        return BlockList;
    }

    public void setBlockList(ArrayList<FileBlock> blockList) {
        BlockList = blockList;
    }
}
