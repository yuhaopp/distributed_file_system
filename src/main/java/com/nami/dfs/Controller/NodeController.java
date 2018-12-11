package com.nami.dfs.Controller;

import com.nami.dfs.Model.ForceStopNodes;
import com.nami.dfs.Model.Node;
import com.nami.dfs.Service.NodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("node")
public class NodeController {
    private NodesService nodesService;

    @Autowired
    public NodeController(NodesService nodesService) {
        this.nodesService = nodesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<Node> getNodesStatus() throws IOException {
        return nodesService.getNodesStatus();
    }

    @RequestMapping(value = "shutdown", method = RequestMethod.POST)
    public ForceStopNodes shutdownNode(String ip) {
        return nodesService.addForceStopNodes(ip);
    }

    @RequestMapping(value = "recover", method = RequestMethod.POST)
    public ForceStopNodes recoverNode(String ip) {
        return nodesService.deleteForceNodes(ip);
    }
}