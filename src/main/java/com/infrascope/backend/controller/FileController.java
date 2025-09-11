package com.infrascope.backend.controller;

import com.infrascope.backend.model.graph.Graph;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {

    Graph process(MultipartFile file);
}
