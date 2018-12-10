package com.nami.dfs.Controller;

import com.nami.dfs.Service.FileChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class FileTransferController {

    @Autowired
    private FileChannelService fcs;

    @RequestMapping(method = RequestMethod.GET)
    public String GetFile(String fileName) {
        fcs.getFile(fileName);
        return "success";
    }
}
