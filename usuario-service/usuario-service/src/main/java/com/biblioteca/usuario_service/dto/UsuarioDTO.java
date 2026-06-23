package com.biblioteca.usuario_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UsuarioDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String telefono;
}
