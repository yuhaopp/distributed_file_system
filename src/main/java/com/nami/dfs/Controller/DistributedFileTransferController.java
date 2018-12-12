package com.nami.dfs.Controller;

import com.nami.dfs.Service.DistributedFileTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("file")
public class DistributedFileTransferController {

    @Value("${nodes.rootPath}")
    private String path;

    private DistributedFileTransferService dfts;

    @Autowired
    public DistributedFileTransferController(DistributedFileTransferService dfts) {
        this.dfts = dfts;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String uploadFilebyPath(String fileName) {
        dfts.uploadFile(fileName);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String downloadFile(String fileName, HttpServletResponse response) throws IOException {
        dfts.downloadFile(fileName);

        File file = new File(path + fileName.replace(".", "_") + "/" + fileName);

        response.setHeader("content-disposition", "attachment;filename=" + fileName);

        try {
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}