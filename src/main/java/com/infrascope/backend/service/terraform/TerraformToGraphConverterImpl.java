package com.infrascope.backend.service.terraform;

import com.infrascope.backend.model.ResourceNode;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.model.graph.GraphEdge;
import com.infrascope.backend.model.graph.GraphNode;
import com.infrascope.backend.service.FileToGraphConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TerraformToGraphConverterImpl implements FileToGraphConverter<List<ResourceNode>> {

    @Override
    public Graph convert(List<ResourceNode> resourceNodes) {
        Graph graph = new Graph();

        for (ResourceNode resourceNode: resourceNodes) {
            GraphNode node = new GraphNode();
            node.setId(resourceNode.name());
            node.setType("terraform_resource");
            node.setLabel(resourceNode.type() + ":" + resourceNode.name());
            node.addMetaData("type", resourceNode.type());
            node.addMetaData("metadata", resourceNode.metadata());

            graph.addNode(node);

//          handle depends_on
            List<String> deps = resourceNode.dependencies();
            if (deps != null) {
                for (String dep: deps) {
                    graph.addEdge(new GraphEdge(dep, resourceNode.name(), "depends_on"));
                }
            }
        }

        return graph;
    }
}
