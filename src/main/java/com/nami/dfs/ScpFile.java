package com.nami.dfs;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

import java.io.IOException;

public class ScpFile {
    private static String IP;
    private static int PORT = 22;
    private static String USER = "user";//登录用户名
    private static String PASSWORD = "123456";//生成私钥的密码和登录密码，这两个共用这个密码
    private static Connection connection;
    private static int BLOCKSIZE = 1024 * 1024 * 64; //64Mb
    //private static String systemLog = FileUtil.currentWorkDir+"systemLog.txt";

    public ScpFile(String ip) {
        IP = ip;
        connection = new Connection(ip, PORT);
    }


    public static boolean isAuthedWithPassword(String user, String password) {
        try {
            return connection.authenticateWithPassword(user, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getFile(String remoteFilePath, String downloadFilePath) {
        try {
            connection.connect();
            boolean isAuthed = isAuthedWithPassword(USER, PASSWORD);
            if (isAuthed) {
                System.out.println("认证成功!");
                SCPClient scpClient = connection.createSCPClient();
                scpClient.get(remoteFilePath, downloadFilePath);
            } else {
                System.out.println("认证失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public static void putFile(String localFile, String remoteTargetDirectory) {
        try {
            connection.connect();
            boolean isAuthed = isAuthedWithPassword(USER, PASSWORD);
            if (isAuthed) {
                SCPClient scpClient = connection.createSCPClient();
                scpClient.put(localFile, remoteTargetDirectory);
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
