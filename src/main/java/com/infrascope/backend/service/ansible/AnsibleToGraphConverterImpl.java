package com.infrascope.backend.service.ansible;

import com.infrascope.backend.model.AnsiblePlay;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.model.graph.GraphEdge;
import com.infrascope.backend.model.graph.GraphNode;
import com.infrascope.backend.service.FileToGraphConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AnsibleToGraphConverterImpl implements FileToGraphConverter<List<AnsiblePlay>> {

    @Override
    public Graph convert(List<AnsiblePlay> ansiblePlays) {
        Graph graph = new Graph();

        for (AnsiblePlay ansiblePlay: ansiblePlays) {
            GraphNode node = new GraphNode();
            node.setId(ansiblePlay.name());
            node.setType("ansible_play");
            node.setLabel(ansiblePlay.name());

            graph.addNode(node);

//          handles the tasks
            List<Map<String, Object>> tasks = ansiblePlay.tasks();
            if (tasks != null) {
                String prevTask = null;

                for (Map<String, Object> task : tasks) {
                    GraphNode taskNode = new GraphNode();

                    taskNode.setId(task.get("name").toString());
                    taskNode.setType("ansible_play");
                    taskNode.setLabel(task.get("name").toString());
                    taskNode.addMetaData("module", task.get("module"));

                    graph.addNode(taskNode);

                    // order edge
                    if (prevTask != null) {
                        graph.addEdge(new GraphEdge(prevTask, task.get("name").toString(), "runs_before"));
                    }
                    prevTask = task.get("name").toString();

                    // link play with task
                    graph.addEdge(new GraphEdge(ansiblePlay.name(), task.get("name").toString(), "contains"));
                }
            }
        }

        return graph;
    }
}
