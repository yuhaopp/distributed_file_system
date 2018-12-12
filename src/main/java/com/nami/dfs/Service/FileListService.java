package com.nami.dfs.Service;

import com.nami.dfs.Model.FileStructure;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;

public class FileListService {

    @Value("${nodes.rootPath}")
    private String path;

    @Value("${nodes.fileListFile}")
    private SerialService fileListFile;

    public ArrayList<FileStructure> getFileList() {
        ArrayList<FileStructure> fileList = new ArrayList<>();
        File file = new File(path + fileListFile);
        if (!file.exists()) {
            SerialService.serial(file, path + fileListFile);
        }
        SerialService.deserial(path + fileListFile, fileList);
        return fileList;
    }

    public void addFile(FileStructure fileStructure) {
        ArrayList<FileStructure> fileList = getFileList();
        File file = new File(path + fileListFile);
        SerialService.deserial(path + fileListFile, fileList);
        fileList.add(fileStructure);
        SerialService.serial(fileList, path + fileListFile);
    }

    public void deleteFile(FileStructure fileStructure) {
        ArrayList<FileStructure> fileList = getFileList();
        File file = new File(path + fileListFile);
        SerialService.deserial(path + fileListFile, fileList);
        fileList.remove(fileStructure);
        SerialService.serial(fileList, path + fileListFile);
    }

    public void updateFile(FileStructure fileStructure) {
        ArrayList<FileStructure> fileList = getFileList();
        File file = new File(path + fileListFile);
        SerialService.deserial(path + fileListFile, fileList);
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getFileName().equals(fileStructure.getFileName())) {
                fileList.remove(i);
                fileList.add(fileStructure);
            }
        }
        SerialService.serial(fileList, path + fileListFile);
    }
}
