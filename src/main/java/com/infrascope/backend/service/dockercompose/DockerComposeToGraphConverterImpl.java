package com.infrascope.backend.service.dockercompose;

import com.infrascope.backend.model.dockercompose.DockerCompose;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.model.graph.GraphEdge;
import com.infrascope.backend.model.graph.GraphNode;
import com.infrascope.backend.service.FileToGraphConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DockerComposeToGraphConverterImpl implements FileToGraphConverter<DockerCompose> {

    @Override
    public Graph convert(DockerCompose dockerCompose) {
        Graph graph = new Graph();

//      handles docker services
        if (dockerCompose.services() != null) {
            dockerCompose.services()
                    .forEach((serviceName, dockerService) -> {
                        GraphNode node = new GraphNode();

                        node.setId(serviceName);
                        node.setType("docker_service");
                        node.setLabel(serviceName);

                        node.addMetaData("image", dockerService.image());
                        node.addMetaData("ports", dockerService.ports());

                        graph.addNode(node);

//                      handles depends_on
                        List<String> deps = dockerService.dependsOn();
                        if (deps != null) {
                            for (String dep : deps) {
                                graph.addEdge(new GraphEdge(serviceName, dep, "depends_on"));
                            }
                        }
            });
        }

//      TODO: handler for docker networks
//      TODO: handler for docker volumes

        return graph;
    }
}
