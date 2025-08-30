package com.infrascope.backend.model;

import java.util.List;
import java.util.Map;

public record ResourceNode(
        String name,
        String type,
        Map<String, Object> metadata,
        List<String> dependencies
) {}
