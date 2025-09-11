package com.infrascope.backend.controller;

import com.infrascope.backend.model.AnsiblePlay;
import com.infrascope.backend.service.FileValidationService;
import com.infrascope.backend.service.parser.AnsibleParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ansible")
public class AnsibleControllerImpl implements FileController<List<AnsiblePlay>> {

    @Autowired
    private FileValidationService fileValidationService;
    @Autowired
    private AnsibleParserImpl ansibleParser;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("yml", "yaml");

    @PostMapping
    public List<AnsiblePlay> process(@RequestParam("file") MultipartFile file) {
        fileValidationService.validateFile(file, ALLOWED_EXTENSIONS);

        return ansibleParser.parse(file);
    }
}
