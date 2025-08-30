package com.infrascope.backend.service.parser;

import com.bertramlabs.plugins.hcl4j.HCLParser;
import com.infrascope.backend.model.ResourceNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TerraformParser {

    /**
     * Parse a .tf file and extract resources
     */
    public List<ResourceNode> parse(File tfFile) {
        List<ResourceNode> resources = new ArrayList<>();

        try {
            // Parse HCL
            HCLParser parser = new HCLParser();
            Map<String, Object> tfMap = parser.parse(tfFile);

            // Terraform "resource" block
            if (tfMap.containsKey("resource")) {
                Map<String, Object> resourceBlocks = (Map<String, Object>) tfMap.get("resource");

                for (String resourceType : resourceBlocks.keySet()) {
                    Map<String, Object> typeBlocks = (Map<String, Object>) resourceBlocks.get(resourceType);

                    for (String resourceName : typeBlocks.keySet()) {
                        Map<String, Object> resourceProps = (Map<String, Object>) typeBlocks.get(resourceName);

                        // Create ResourceNode
                        ResourceNode node = new ResourceNode(
                                resourceName,
                                resourceType,
                                resourceProps,
                                extractDependsOn(resourceProps)
                        );

                        resources.add(node);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Terraform file: " + tfFile.getName(), e);
        }

        return resources;
    }

    /**
     * Extract depends_on from resource properties
     */
    private List<String> extractDependsOn(Map<String, Object> resourceProps) {
        List<String> depends = new ArrayList<>();

        if (resourceProps.containsKey("depends_on")) {
            Object depObj = resourceProps.get("depends_on");

            if (depObj instanceof List) {
                for (Object dep : (List<?>) depObj) {
                    depends.add(dep.toString());
                }
            } else {
                depends.add(depObj.toString());
            }
        }
        return depends;
    }
}
