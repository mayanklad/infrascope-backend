package com.infrascope.backend.controller;

import com.infrascope.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private FileService fileService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "tf",
            "yml",
            "yaml",
            "docker-compose.yml",
            "docker-compose.yaml"
    );

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseEntity<String> validateResponse = validateFile(file);

        if (validateResponse != null) {
            return validateResponse;
        }

        return fileService.handleFileUpload(file);
    }

    private ResponseEntity<String> validateFile(MultipartFile file) {
        String filename = file.getOriginalFilename().trim();

        if (file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("File is empty.");
        }

        if (filename.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("File name is empty.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File size exceeds 5MB limit.");
        }

        boolean allowed = ALLOWED_EXTENSIONS.stream()
                .anyMatch(ext -> filename.equalsIgnoreCase(ext) || filename.endsWith("." + ext));

        if (!allowed) {
            return ResponseEntity
                    .badRequest()
                    .body("Unsupported file type: " + filename);
        }

        return null;
    }
}
