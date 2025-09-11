package com.infrascope.backend.controller;

import com.infrascope.backend.model.ResourceNode;
import com.infrascope.backend.service.FileValidationService;
import com.infrascope.backend.service.parser.TerraformParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/terraform")
public class TerraformController {

    @Autowired
    private FileValidationService fileValidationService;
    @Autowired
    private TerraformParser terraformParser;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("tf");

    @PostMapping
    public List<ResourceNode> process(@RequestParam("file") MultipartFile file) {
        fileValidationService.validateFile(file, ALLOWED_EXTENSIONS);

        return terraformParser.parse(file);
    }
}
