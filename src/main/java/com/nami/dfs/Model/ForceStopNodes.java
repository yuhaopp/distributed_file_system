package com.nami.dfs.Model;

import java.io.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ForceStopNodes implements Serializable {
    private ArrayList<String> nodeList;

    public ForceStopNodes() {
    }

    public ForceStopNodes(ArrayList<String> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<String> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<String> nodeList) {
        this.nodeList = nodeList;
    }
}
