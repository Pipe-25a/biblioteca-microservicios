package com.biblioteca.libro_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// DTO para representar la solicitud de creación o actualización de un libro, con validaciones para los campos.
public class LibroRequest {

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;
    @NotNull(message = "El ID del autor es obligatorio")
    private Long authorId;

    @NotNull(message = "El campo disponible es obligatorio")
    private Boolean disponible;
}
