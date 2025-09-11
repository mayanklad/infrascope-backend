package com.infrascope.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Object process(MultipartFile file);
}
