package com.usuarios.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse(
        List<UserResponse> content,
        int page,
        int size,
        Long totalElements
) {

}
