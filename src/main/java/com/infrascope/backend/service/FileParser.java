package com.infrascope.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileParser<T> {

    T parse(MultipartFile file);
}
