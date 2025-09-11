package com.infrascope.backend.controller;

import org.springframework.web.multipart.MultipartFile;

public interface FileController<T> {

    T process(MultipartFile file);
}
