package com.biblioteca.autenticacion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutenticacionResponse {
    private Long id;
    private String nombreUsuario;
}