package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@ConfigurationProperties(prefix = "nodes")
public class FileChannelService {
    //    @Value("${nodes.IP}")
    private String IP;

    //    @Value("${nodes.PORT}")
    private int PORT;

    //    @Value("${nodes.USERNAME}")
    private String USERNAME;

    //    @Value("${nodes.USERNAME}")
    private String PASSWORD;

    //    @Value("${nodes.blockSize}")
    private int blockSize;

    //    @Value("${nodes.remoteFilePath}")
    private String remoteFilePath;

    //    @Value("${nodes.downloadFilePath}")
    private String downloadFilePath;

    private Connection connection;

    public boolean isAuthencatedWithPassword(String username, String password) {
        try {
            return connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getFile(String fileName) {
        try {
            connection = new Connection(IP, PORT);
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

    public void putFile(String localFilePath, String remoteFilePath) {
        try {
            connection = new Connection(IP, PORT);
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

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    public void setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
