package com.biblioteca.reporte_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReporteDTO {

    @NotBlank
    private String tipo;
}