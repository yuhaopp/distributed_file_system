package com.nami.dfs.ViewModel;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FileDetail {
    private String fileName;
    private double fileSize;
    private boolean fileStatus;
    private ArrayList<FileBlockWithNodeInformation> FileBlockWithNodeInformation;

    public FileDetail(String fileName, double fileSize, boolean fileStatus, ArrayList<FileBlockWithNodeInformation> fileBlockWithNodeInformation) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileStatus = fileStatus;
        FileBlockWithNodeInformation = fileBlockWithNodeInformation;
    }

    public FileDetail() {
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

    public ArrayList<FileBlockWithNodeInformation> getFileBlockWithNodeInformation() {
        return FileBlockWithNodeInformation;
    }

    public void setFileBlockWithNodeInformation(ArrayList<FileBlockWithNodeInformation> fileBlockWithNodeInformation) {
        FileBlockWithNodeInformation = fileBlockWithNodeInformation;
    }
}
