package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileChannelService {

    @Value("${nodes.port}")
    private int PORT;

    @Value("${nodes.username}")
    private String USERNAME;

    @Value("${nodes.password}")
    private String PASSWORD;

    @Value("${nodes.blockSize}")
    private int blockSize;

    @Value("${nodes.remoteFilePath}")
    private String remoteFilePath;

    private Connection connection;

    public boolean isAuthencatedWithPassword(String username, String password) {
        try {
            return connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getFile(String fileName, String ip, String downloadFilePath) {
        try {
            connection = new Connection(ip, PORT);
            connection.connect();
            boolean isAuthed = isAuthencatedWithPassword(USERNAME, PASSWORD);
            if (isAuthed) {
                System.out.println("认证成功!");
                SCPClient scpClient = connection.createSCPClient();
                scpClient.get(remoteFilePath + fileName, downloadFilePath);
            } else {
                System.out.println("认证失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void putFile(String localFilePath, String ip) {
        try {
            connection = new Connection(ip, PORT);
            connection.connect();
            boolean isAuthed = isAuthencatedWithPassword(USERNAME, PASSWORD);
            if (isAuthed) {
                SCPClient scpClient = connection.createSCPClient();
                scpClient.put(localFilePath, remoteFilePath);
            } else {
                System.out.println("认证失败!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
