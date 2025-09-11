package com.infrascope.backend.controller;

import com.infrascope.backend.service.FileValidationService;
import com.infrascope.backend.service.parser.TerraformParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> process(@RequestParam("file") MultipartFile file) {
        return fileValidationService.validateFile(file, ALLOWED_EXTENSIONS)
                .orElseGet(() -> ResponseEntity.ok(terraformParser.parse(file).toString()));
    }
}
