package com.nami.dfs.Model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FileStructure {
    private String fileName;
    private double fileSize;
    private boolean fileStatus;
    private ArrayList<FileBlock> BlockList;

    public FileStructure() {
    }

    public FileStructure(String fileName, double fileSize, boolean fileStatus, ArrayList<FileBlock> blockList) {
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

    public boolean isFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(boolean fileStatus) {
        this.fileStatus = fileStatus;
    }

    public ArrayList<FileBlock> getBlockList() {
        return BlockList;
    }

    public void setBlockList(ArrayList<FileBlock> blockList) {
        BlockList = blockList;
    }
}
