package com.infrascope.backend.model;

import java.util.List;
import java.util.Map;

public record AnsiblePlay(
        String name,
        List<String> hosts,
        List<String> roles,
        List<Map<String, Object>> tasks
) {}
