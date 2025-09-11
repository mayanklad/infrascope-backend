package com.infrascope.backend.service;

import com.infrascope.backend.model.graph.Graph;

public interface FileToGraphConverter<T> {

    Graph convert(T t);
}
