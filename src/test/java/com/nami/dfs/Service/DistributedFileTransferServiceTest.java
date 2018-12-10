package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DistributedFileTransferServiceTest {

    @Test
    public void turnOffRemoteNode() {
        try {
            Connection conn = new Connection("192.168.17.129");

            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword("user", "123456");
            if (isAuthenticated == false) {
                return;
            }
            System.out.println("认证成功");
            Session sess = conn.openSession();
            sess.execCommand("su root;123456;sh ~/shutdown.sh");
            sess.close();
            System.out.println("执行成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}