package com.nami.dfs.Controller;

import com.nami.dfs.Service.DistributedFileTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class DistributedFileTransferController {

    @Autowired
    private DistributedFileTransferService dfts;

    @RequestMapping(method = RequestMethod.PUT)
    public String Upload(String fileName) {
        dfts.uploadFile(fileName);
        return "success";
    }
}
