package com.nami.dfs;

public class Main {
    public static void main(String args[]) {
        String ip = "192.168.17.129";
        String remoteFilePath = "/home/user/";
        String downloadFilePath = "C:\\Users\\yuhao\\Desktop\\";
        String fileName = "test.txt";
        ScpFile scpChannel = new ScpFile(ip);

        scpChannel.getFile(remoteFilePath + fileName, downloadFilePath);
    }
}