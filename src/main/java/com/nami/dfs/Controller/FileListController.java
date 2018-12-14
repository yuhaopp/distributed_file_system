package com.nami.dfs.Controller;

import com.nami.dfs.Model.FileStructure;
import com.nami.dfs.Service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("fileList")
public class FileListController {
    private FileListService fileListService;

    @Autowired
    public FileListController(FileListService fileListService) {
        this.fileListService = fileListService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<FileStructure> getFileList() throws IOException {
        return fileListService.getFileList();
    }
}
