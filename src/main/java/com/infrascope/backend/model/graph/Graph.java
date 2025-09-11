package com.infrascope.backend.model.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Graph {

    private List<GraphNode> nodes;
    private List<GraphEdge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(GraphNode node) {
        this.nodes.add(node);
    }

    public void addEdge(GraphEdge edge) {
        this.edges.add(edge);
    }
}
