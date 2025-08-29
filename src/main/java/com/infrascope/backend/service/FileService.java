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
public class FileService {

    private final Path uploadDir = Path.of("uploads");

    public FileService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public ResponseEntity<String> handleFileUpload(MultipartFile file) {
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
                .body(response.toString());
    }
}
