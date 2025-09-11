package com.infrascope.backend.service.parser;

import com.infrascope.backend.model.DockerCompose;
import com.infrascope.backend.model.DockerNetwork;
import com.infrascope.backend.model.DockerService;
import com.infrascope.backend.model.DockerVolume;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DockerComposeParser {

    public DockerCompose parse(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(new FileReader(file));

//      Parse services
        Map<String, DockerService> serviceModels = new HashMap<>();
        Map<String, Object> services = (Map<String, Object>) data.get("services");
        if (services != null) {
            for (Map.Entry<String, Object> entry : services.entrySet()) {
                String serviceName = entry.getKey();
                Map<String, Object> serviceData = (Map<String, Object>) entry.getValue();

                DockerService service = new DockerService(
                        (String) serviceData.get("image"),
                        (List<String>) serviceData.get("ports"),
                        (Map<String, String>) serviceData.get("environment"),
                        (List<String>) serviceData.get("depends_on")
                );

                serviceModels.put(serviceName, service);
            }
        }

        // Parse networks
        Map<String, DockerNetwork> networkModels = new HashMap<>();
        Map<String, Object> networks = (Map<String, Object>) data.get("networks");
        if (networks != null) {
            for (Map.Entry<String, Object> entry : networks.entrySet()) {
                String networkName = entry.getKey();
                Map<String, Object> networkData = (Map<String, Object>) entry.getValue();

                DockerNetwork network = new DockerNetwork(
                        (String) networkData.getOrDefault("driver", "bridge")
                );

                networkModels.put(networkName, network);
            }
        }

        // Parse volumes
        Map<String, DockerVolume> volumeModels = new HashMap<>();
        Map<String, Object> volumes = (Map<String, Object>) data.get("volumes");
        if (volumes != null) {
            for (Map.Entry<String, Object> entry : volumes.entrySet()) {
                String volumeName = entry.getKey();
                Map<String, Object> volumeData = (Map<String, Object>) entry.getValue();

                DockerVolume volume = new DockerVolume(
                        (String) volumeData.getOrDefault("driver", "local")
                );

                volumeModels.put(volumeName, volume);
            }
        }

        return new DockerCompose(
                serviceModels,
                networkModels,
                volumeModels
        );
    }
}
