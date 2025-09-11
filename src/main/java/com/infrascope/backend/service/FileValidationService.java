package com.infrascope.backend.service;

import com.infrascope.backend.exception.EmptyFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileValidationService {

    public void validateFile(MultipartFile file, Set<String> allowedExtensions) {
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename().trim() : "";

        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty.");
        }

        if (filename.isBlank()) {
            throw new EmptyFileException("File name is empty.");
        }

        boolean allowed = allowedExtensions.stream()
                .anyMatch(ext -> filename.equalsIgnoreCase(ext) || filename.endsWith("." + ext));

        if (!allowed) {
            throw new UnsupportedOperationException("Unsupported file type: " + filename);
        }
    }
}
