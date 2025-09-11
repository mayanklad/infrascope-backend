package com.infrascope.backend.service.ansible;

import com.infrascope.backend.model.AnsiblePlay;
import com.infrascope.backend.service.FileParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnsibleParserImpl implements FileParser<List<AnsiblePlay>> {

    @Override
    public List<AnsiblePlay> parse(MultipartFile file) {
        Yaml parser = new Yaml();
        List<Object> playMapList = null;

        try {
            playMapList = parser.load(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Terraform file: " + file.getName(), e);
        }

        List<AnsiblePlay> plays = new ArrayList<>();

        for (Object block : playMapList) {
            if (block instanceof Map<?,?> playBlock) {
//              Extract name
                String name = (String) playBlock.get("name");

//              Extract hosts
                List<String> hosts = new ArrayList<>();
                if (playBlock.get("hosts") instanceof String) {
                    hosts = List.of((String) playBlock.get("hosts"));
                } else if (playBlock.get("hosts") instanceof List) {
                    hosts = (List<String>) playBlock.get("hosts");
                }

//              Extract roles
                List<String> roles = new ArrayList<>();
                if (playBlock.get("roles") instanceof String) {
                    roles = List.of((String) playBlock.get("roles"));
                } else if (playBlock.get("roles") instanceof List) {
                    roles = (List<String>) playBlock.get("roles");
                }

//              Extract tasks
                List<Map<String, Object>> tasks = new ArrayList<>();
                if (playBlock.get("tasks") instanceof List) {
                    tasks = (List<Map<String, Object>>) playBlock.get("tasks");
                }

                plays.add(
                        new AnsiblePlay(
                                name,
                                hosts,
                                roles,
                                tasks
                        )
                );
            }
        }

        return plays;
    }
}
