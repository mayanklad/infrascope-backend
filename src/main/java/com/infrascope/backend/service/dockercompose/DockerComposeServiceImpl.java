package com.infrascope.backend.service.dockercompose;

import com.infrascope.backend.model.dockercompose.DockerCompose;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DockerComposeServiceImpl implements FileService {

    @Autowired
    private DockerComposeParserImpl parser;
    @Autowired
    private DockerComposeToGraphConverterImpl converter;

    @Override
    public Graph process(MultipartFile file) {
        DockerCompose parsed = parser.parse(file);

        return converter.convert(parsed);
    }
}
