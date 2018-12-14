package com.nami.dfs.Service;

import com.nami.dfs.Model.FileBlock;
import com.nami.dfs.Model.FileStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class FileListService {

    @Value("${nodes.rootPath}")
    private String path;

    @Value("${nodes.fileListFile}")
    private String fileListFile;

    private NodesService nodesService;

    @Autowired
    public FileListService(NodesService nodesService) {
        this.nodesService = nodesService;
    }

    public ArrayList<FileStructure> getFileList() throws IOException {
        ArrayList<FileStructure> fileStructures = new ArrayList<>();
        File file = new File(path + fileListFile);
        if (file.exists()) {
            fileStructures = (ArrayList<FileStructure>) SerialService.deserial(path + fileListFile, fileStructures);
        }
        return fileStructures;
    }

    public FileStructure findFileByFileName(String fileName) throws IOException {
        ArrayList<FileStructure> fileList = getFileList();
        for (FileStructure file : fileList) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    public ArrayList<FileStructure> deleteFileByFileName(String fileName) throws IOException {
        ArrayList<FileStructure> fileList = getFileList();
        for (FileStructure file : fileList) {
            if (file.getFileName().equals(fileName)) {
                fileList.remove(file);
                break;
            }
        }
        SerialService.serial(fileList, path + fileListFile);
        return fileList;
    }

    public void addFile(FileStructure fileStructure) throws IOException {
        File file = new File(path + fileListFile);
        ArrayList<FileStructure> fileList = new ArrayList<>();
        if (!file.exists()) {
            fileList.add(fileStructure);
        } else {
            fileList = getFileList();
            fileList.add(fileStructure);
        }
        SerialService.serial(fileList, path + fileListFile);
    }

    public void updateFile(FileStructure fileStructure) throws IOException {
        ArrayList<FileStructure> fileList = getFileList();
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getFileName().equals(fileStructure.getFileName())) {
                fileList.remove(i);
                fileList.add(fileStructure);
            }
        }
        SerialService.serial(fileList, path + fileListFile);
    }
}
