package com.infrascope.backend.controller;

import com.infrascope.backend.model.DockerCompose;
import com.infrascope.backend.service.FileValidationService;
import com.infrascope.backend.service.parser.DockerComposeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/dockercompose")
public class DockerComposeController {

    @Autowired
    private FileValidationService fileValidationService;
    @Autowired
    private DockerComposeParser dockerComposeParser;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("docker-compose.yml", "docker-compose.yaml");

    @PostMapping
    public DockerCompose process(@RequestParam("file") MultipartFile file) {
        fileValidationService.validateFile(file, ALLOWED_EXTENSIONS);
        
        return dockerComposeParser.parse(file);
    }
}
