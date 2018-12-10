package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

import java.io.IOException;

public class FileChannelService {
    private String IP;
    private static int PORT = 22;
    private static String USERNAME = "user";
    private static String PASSWORD = "123456";
    private static Connection connection;
    private static int BLOCKSIZE = 1024 * 1024 * 64;

    public FileChannelService(String ip) {
        IP = ip;
        connection = new Connection(ip, PORT);
    }

    public boolean isAuthencatedWithPassword(String username, String password) {
        try {
            return connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getFile(String remoteFilePath, String localFilePath) {
        try {
            connection.connect();
            boolean isAuthed = isAuthencatedWithPassword(USERNAME, PASSWORD);
            if (isAuthed) {
                System.out.println("认证成功!");
                SCPClient scpClient = connection.createSCPClient();
                scpClient.get(remoteFilePath, localFilePath);
            } else {
                System.out.println("认证失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void putFile(String localFilePath, String remoteFilePath) {
        try {
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
