package com.biblioteca.libro_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

// DTO para representar la respuesta de error de la API, con detalles como timestamp, status, error, mensaje, path y una lista de errores específicos.
@Data
@Builder
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<String> errors;
}