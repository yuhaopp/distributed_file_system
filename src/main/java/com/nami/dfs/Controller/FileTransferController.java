package com.nami.dfs.Controller;

import com.nami.dfs.Service.FileChannelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class FileTransferController {
    String ip = "192.168.17.129";
    String remoteFilePath = "/home/user/";
    String downloadFilePath = "C:\\Users\\yuhao\\Desktop\\";

    FileChannelService fcs = new FileChannelService(ip);

    @RequestMapping(method = RequestMethod.GET)
    public String GetFile(String fileName) {
        fcs.getFile(remoteFilePath + fileName, downloadFilePath);
        return "success";
    }
}