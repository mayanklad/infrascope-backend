package com.infrascope.backend.model.dockercompose;

import java.util.Map;

public record DockerCompose(
        Map<String, DockerService> services,
        Map<String, DockerNetwork> networks,
        Map<String, DockerVolume> volumes
) {}
