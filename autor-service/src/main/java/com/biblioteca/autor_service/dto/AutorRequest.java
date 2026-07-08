package com.biblioteca.autor_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorRequest {

    @NotBlank(message = "El nombre del autor no puede estar vacío")
     @Size(max = 100, message = "El nombre del autor no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La nacionalidad no puede estar vacía")
    @Size(max = 100, message = "La nacionalidad no puede exceder los 100 caracteres")
    private String nacionalidad;
}
