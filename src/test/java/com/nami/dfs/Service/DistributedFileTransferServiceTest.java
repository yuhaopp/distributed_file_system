package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            sess.execCommand("du -s dfs");
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
            sess.close();
            System.out.println("执行成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}