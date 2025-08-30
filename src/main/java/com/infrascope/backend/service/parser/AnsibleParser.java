package com.infrascope.backend.service.parser;

import com.infrascope.backend.model.AnsiblePlay;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnsibleParser {

    public List<AnsiblePlay> parse(File file) throws FileNotFoundException {
        Yaml parser = new Yaml();
        List<Object> playMapList = parser.load(new FileReader(file));
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
