package com.biblioteca.multa_service.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data; 
@Data
@Builder

// DTO para representar la respuesta de error de la API, con detalles como timestamp, status, error, mensaje, path y una lista de errores específicos.
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    @Builder.Default
    private List<String> errors = new ArrayList<>();
}
