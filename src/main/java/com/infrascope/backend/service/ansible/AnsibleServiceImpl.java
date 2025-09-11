package com.infrascope.backend.service.ansible;

import com.infrascope.backend.model.AnsiblePlay;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AnsibleServiceImpl implements FileService {

    @Autowired
    private AnsibleParserImpl parser;
    @Autowired
    private AnsibleToGraphConverterImpl converter;

    @Override
    public Graph process(MultipartFile file) {
        List<AnsiblePlay> parsed = parser.parse(file);

        return converter.convert(parsed);
    }
}
