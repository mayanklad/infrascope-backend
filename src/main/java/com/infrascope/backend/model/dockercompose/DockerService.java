package com.infrascope.backend.model.dockercompose;

import java.util.List;
import java.util.Map;

public record DockerService(
        String image,
        List<String> ports,
        Map<String, String> environments,
        List<String> dependsOn
) {}
