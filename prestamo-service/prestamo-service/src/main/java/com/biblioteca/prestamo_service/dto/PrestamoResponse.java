package com.biblioteca.prestamo_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// DTO para representar la respuesta de un préstamo, con detalles como ID, usuario, libro, fecha de préstamo y estado.
public class PrestamoResponse {
    private Long id;
    private Long usuarioId;
    private Long libroId;
    private LocalDate fechaPrestamo;
    private String estado;

}
