package com.nami.dfs.Model;

import java.util.ArrayList;

public class FileList {
    private ArrayList<File> fileList;

    public ArrayList<File> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<File> fileList) {
        this.fileList = fileList;
    }

    public FileList() {
    }

    public FileList(ArrayList<File> fileList) {
        this.fileList = fileList;
    }

}