package com.nami.dfs;

import com.nami.dfs.Service.FileChannelService;

public class Main {

    public static void main(String args[]) {
        String ip = "192.168.17.129";
        String remoteFilePath = "/home/user/";
        String downloadFilePath = "C:\\Users\\yuhao\\Desktop\\";
        String fileName = "test.txt";
        FileChannelService fcs = new FileChannelService(ip);

        fcs.getFile(remoteFilePath + fileName, downloadFilePath);
    }
}