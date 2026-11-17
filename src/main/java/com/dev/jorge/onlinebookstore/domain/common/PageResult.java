package com.dev.jorge.onlinebookstore.domain.common;

import java.util.List;

public record PageResult<T>(List<T> content, int page, int size, long totalElements) {
    public PageResult {
        content = List.copyOf(content);
    }
}
