package com.infrascope.backend.service;

import com.infrascope.backend.model.graph.Graph;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Graph process(MultipartFile file);
}
