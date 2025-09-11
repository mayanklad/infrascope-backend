package com.infrascope.backend.controller;

import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.service.FileValidationService;
import com.infrascope.backend.service.dockercompose.DockerComposeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/dockercompose")
public class DockerComposeControllerImpl implements FileController {

    @Autowired
    private FileValidationService fileValidationService;
    @Autowired
    private DockerComposeServiceImpl dockerComposeService;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("docker-compose.yml", "docker-compose.yaml");

    @Override
    @PostMapping
    public Graph process(@RequestParam("file") MultipartFile file) {
        fileValidationService.validateFile(file, ALLOWED_EXTENSIONS);
        
        return dockerComposeService.process(file);
    }
}
