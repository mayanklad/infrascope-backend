package com.infrascope.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@Service
public class FileValidationService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public Optional<ResponseEntity<String>> validateFile(MultipartFile file, Set<String> allowedExtensions) {
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename().trim() : "";

        if (file.isEmpty()) {
            return Optional.of(ResponseEntity
                    .badRequest()
                    .body("File is empty."));
        }

        if (filename.isBlank()) {
            return Optional.of(ResponseEntity
                    .badRequest()
                    .body("File name is empty."));
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return Optional.of(ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File size exceeds 5MB limit."));
        }

        boolean allowed = allowedExtensions.stream()
                .anyMatch(ext -> filename.equalsIgnoreCase(ext) || filename.endsWith("." + ext));

        if (!allowed) {
            return Optional.of(ResponseEntity
                    .badRequest()
                    .body("Unsupported file type: " + filename));
        }

        return Optional.empty();
    }
}
