package com.infrascope.backend.model.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class GraphNode {

    private String id;
    private String type;
    private String label;
    private Map<String, Object> metaData;

    public GraphNode() {
        this.metaData = new HashMap<>();
    }

    public void addMetaData(String key, Object value) {
        this.metaData.put(key, value);
    }
}
