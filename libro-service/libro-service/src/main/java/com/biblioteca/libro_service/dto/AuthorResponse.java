package com.biblioteca.libro_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

// DTO para representar la respuesta de un autor, con su ID y nombre completo.
public class AuthorResponse {
    private Long id;
    private String fullName;
}
