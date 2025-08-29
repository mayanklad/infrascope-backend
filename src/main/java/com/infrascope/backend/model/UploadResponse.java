package com.infrascope.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {

    private String originalFilename;
    private String storedFilename;
    private String path;
}
