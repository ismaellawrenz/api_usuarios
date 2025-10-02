package com.usuarios.dto;

import java.time.LocalDateTime;

public record UserResponse(Long id, String nome, String email, LocalDateTime dataCriacao) {
}
