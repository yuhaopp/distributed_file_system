package com.nami.dfs.Controller;

import com.nami.dfs.Model.FileStructure;
import com.nami.dfs.Service.DistributedFileTransferService;
import com.nami.dfs.Service.FileService;
import com.nami.dfs.ViewModel.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@RestController
@RequestMapping("file")
public class DistributedFileTransferController {

    @Value("${nodes.rootPath}")
    private String path;

    private DistributedFileTransferService dfts;
    private FileService fileService;

    @Autowired
    public DistributedFileTransferController(DistributedFileTransferService dfts, FileService fileService) {
        this.dfts = dfts;
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ArrayList<FileStructure> uploadFilebyPath(String fileName) throws IOException {
        return dfts.uploadFile(fileName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "uploadFile")
    public ArrayList<FileStructure> uploadFileByFileStream(@RequestParam("file") MultipartFile file) throws IOException {
        return dfts.uploadFileByFileStream(file);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void downloadFile(String fileName, HttpServletResponse response) throws IOException {
        dfts.downloadFile(fileName);

        File file = new File(path+fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }

    @RequestMapping(method = RequestMethod.GET, value = "detail")
    public FileDetail getFileDetail(String fileName) throws IOException {
        return fileService.getFileDetail(fileName);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ArrayList<FileStructure> deleteFile(String fileName) throws IOException {
        return dfts.deleteFile(fileName);
    }
}