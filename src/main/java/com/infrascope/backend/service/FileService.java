package com.infrascope.backend.service;

import com.infrascope.backend.model.FileUploadResponse;
import com.infrascope.backend.service.parser.AnsibleParser;
import com.infrascope.backend.service.parser.TerraformParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private final Path uploadDir = Path.of("uploads");
    @Autowired
    private TerraformParser terraformParser;
    @Autowired
    private AnsibleParser ansibleParser;

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

//          Parse if .tf file
            if (sanitizedFilename.endsWith(".tf")) {
                System.out.println(terraformParser.parse(targetPath.toFile()).toString());
            } else if (sanitizedFilename.endsWith(".yml") || sanitizedFilename.endsWith(".yaml")) {
                System.out.println(ansibleParser.parse(targetPath.toFile()).toString());
            } else {
                System.out.println("No parser available for this file type.");
            }

        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to store file: " + file.getOriginalFilename());
        }

        FileUploadResponse response = new FileUploadResponse(file.getOriginalFilename(), uniqueName, targetPath.toString());

        return ResponseEntity
                .created(URI.create(response.getPath()))
                .body(response.toString());
    }
}
