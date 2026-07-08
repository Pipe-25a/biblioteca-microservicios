package com.biblioteca.prestamo_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// DTO para representar la solicitud de creación o actualización de un préstamo, con validaciones para los campos.
public class PrestamoRequest {
    @NotNull(message="El ID de Usuario es obligatorio")
    private Long usuarioId;
    @NotNull(message="EL Id Del libro es obligatorio")
    private Long libroId;
    @NotNull(message = "La fecha de Prestamo es obligatoria")
    private LocalDate fechaPrestamo;
    //libre-reservado-prestado
    private String estado;
}
