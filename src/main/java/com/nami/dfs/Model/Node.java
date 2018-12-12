package com.nami.dfs.Model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Node implements Serializable, Comparable<Node> {
    private int nodeID;
    private String nodeIP;
    private boolean nodeStatus;
    private double nodeStorageSize;
    private double nodeUsedSize;

    public Node() {

    }

    public Node(int nodeID, String nodeIP, boolean nodeStatus, double nodeStorageSize, double nodeUsedSize) {
        this.nodeID = nodeID;
        this.nodeIP = nodeIP;
        this.nodeStatus = nodeStatus;
        this.nodeStorageSize = nodeStorageSize;
        this.nodeUsedSize = nodeUsedSize;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeIP() {
        return nodeIP;
    }

    public void setNodeIP(String nodeIP) {
        this.nodeIP = nodeIP;
    }

    public boolean getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(boolean nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public double getNodeStorageSize() {
        return nodeStorageSize;
    }

    public void setNodeStorageSize(double nodeStorageSize) {
        this.nodeStorageSize = nodeStorageSize;
    }

    public double getNodeUsedSize() {
        return nodeUsedSize;
    }

    public void setNodeUsedSize(double nodeUsedSize) {
        this.nodeUsedSize = nodeUsedSize;
    }

    @Override
    public int compareTo(Node o) {
        return (Double.compare(this.nodeUsedSize, o.nodeUsedSize));
    }
}
