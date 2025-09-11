package com.infrascope.backend.service.terraform;

import com.infrascope.backend.model.ResourceNode;
import com.infrascope.backend.model.graph.Graph;
import com.infrascope.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TerraformServiceImpl implements FileService {

    @Autowired
    private TerraformParserImpl parser;
    @Autowired
    private TerraformToGraphConverterImpl converter;

    @Override
    public Graph process(MultipartFile file) {
        List<ResourceNode> parsed = parser.parse(file);

        return converter.convert(parsed);
    }
}
