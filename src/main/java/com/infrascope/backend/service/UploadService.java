package com.infrascope.backend.service;

import com.infrascope.backend.model.UploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class UploadService {

    private final Path uploadDir = Path.of("uploads");

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "tf",
            "yml",
            "yaml",
            "docker-compose.yml",
            "docker-compose.yaml"
    );

    public UploadService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public void validateFileType(MultipartFile file) throws IllegalArgumentException {
        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name is empty.");
        }

        boolean allowed = ALLOWED_EXTENSIONS.stream()
                .anyMatch(ext -> filename.equalsIgnoreCase(ext) || filename.endsWith("." + ext));

        if (!allowed) {
            throw new IllegalArgumentException("Invalid file type: " + filename);
        }
    }

    public ResponseEntity<?> handleFileUpload(MultipartFile file) {
        try {
            validateFileType(file);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

//      5 MB limit
        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File size exceeds 5MB limit.");
        }

//      Sanitize filename
        String sanitizedFilename = file.getOriginalFilename().trim().replaceAll("[^a-zA-Z0-9_.-]", "_");
        String uniqueName = UUID.randomUUID() + "-" + sanitizedFilename;

        Path targetPath = uploadDir.resolve(uniqueName);

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to store file: " + file.getOriginalFilename());
        }

        UploadResponse response = new UploadResponse(file.getOriginalFilename(), uniqueName, targetPath.toString());

        return ResponseEntity
                .created(URI.create(response.getPath()))
                .body(response);
    }
}
