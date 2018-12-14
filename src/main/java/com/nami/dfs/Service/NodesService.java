package com.nami.dfs.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.nami.dfs.Model.ForceStopNodes;
import com.nami.dfs.Model.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class NodesService {
    @Value("#{'${nodes.ips}'.split(',')}")
    private ArrayList<String> ipList;

    @Value("${nodes.size}")
    private long nodeSize;

    @Value("${nodes.username}")
    private String username;

    @Value("${nodes.password}")
    private String password;

    @Value("${nodes.port}")
    private int port;

    @Value("${nodes.forceStopNodesFile}")
    private String forceStopNodesFile;

    @Value("${nodes.rootPath}")
    private String path;

    public ArrayList<Node> getNodesStatus() throws IOException {
        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<String> forceStopNodes = getForceStopNodes();
        for (int i = 0; i < ipList.size(); i++) {
            Node node = new Node();
            node.setNodeID(i);
            node.setNodeIP(ipList.get(i));
            node.setNodeStorageSize(nodeSize);
            node.setNodeStatus(isConnected(node.getNodeIP()));
            //check force stop nodes
            if (forceStopNodes != null) {
                if (forceStopNodes.contains(node.getNodeIP())) {
                    node.setNodeStatus(false);
                }
            }
            node.setNodeUsedSize(getNodeUsedSize(node.getNodeIP()));
            nodeList.add(node);
        }
        return nodeList;
    }

    public ArrayList<String> getForceStopNodes() {
        ForceStopNodes forceStopNodes = new ForceStopNodes();
        File file = new File(path + forceStopNodesFile);
        if (file.exists()) {
            forceStopNodes = (ForceStopNodes) SerialService.deserial(path + forceStopNodesFile, forceStopNodes);
            return forceStopNodes.getNodeList();
        }
        return forceStopNodes.getNodeList();
    }

    public ForceStopNodes addForceStopNodes(String ip) {
        File file = new File(path + forceStopNodesFile);

        ForceStopNodes forceStopNodes = new ForceStopNodes();
        ArrayList<String> nodeList = new ArrayList<>();
        if (!file.exists()) {
            nodeList.add(ip);
            forceStopNodes.setNodeList(nodeList);
            SerialService.serial(forceStopNodes, path + forceStopNodesFile);
        } else {
            forceStopNodes = (ForceStopNodes) SerialService.deserial(path + forceStopNodesFile, forceStopNodes);
            nodeList = forceStopNodes.getNodeList();
            nodeList.add(ip);
            forceStopNodes.setNodeList(nodeList);
            SerialService.serial(forceStopNodes, path + forceStopNodesFile);
        }
        return forceStopNodes;
    }

    public ForceStopNodes deleteForceNodes(String ip) {
        ForceStopNodes forceStopNodes = new ForceStopNodes();
        ArrayList<String> nodeList = new ArrayList<>();
        forceStopNodes = (ForceStopNodes) SerialService.deserial(path + forceStopNodesFile, forceStopNodes);
        nodeList = forceStopNodes.getNodeList();
        nodeList.remove(ip);
        forceStopNodes.setNodeList(nodeList);
        SerialService.serial(forceStopNodes, path + forceStopNodesFile);
        return forceStopNodes;
    }

    public long getNodeUsedSize(String ip) {
        try {
            Connection conn = new Connection(ip);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword("user", "123456");
            if (isAuthenticated == false) {
                return 0;
            }
            Session sess = conn.openSession();
            sess.execCommand("du -s dfs");
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            String storage = stdoutReader.readLine();
            sess.close();
            if (storage == null) {
                return 0;
            } else {
                String[] storageList = storage.split("\t");
                storage = storageList[0];
                return Long.parseLong(storage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isConnected(String ip) throws IOException {
        Connection connection = new Connection(ip, port);
        connection.connect();
        try {
            return connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Node> sortNodesByStorage() throws IOException {
        ArrayList<Node> nodes = getNodesStatus();
        Collections.sort(nodes);
        for (Node node : nodes) {
            if (!node.getNodeStatus()) {
                nodes.remove(node);
            }
        }
        return nodes;
    }

    public Node getNodeByIp(String ip) throws IOException {
        ArrayList<Node> nodes = getNodesStatus();
        for (Node node : nodes) {
            if (node.getNodeIP().equals(ip)) {
                return node;
            }
        }
        return null;
    }
}
