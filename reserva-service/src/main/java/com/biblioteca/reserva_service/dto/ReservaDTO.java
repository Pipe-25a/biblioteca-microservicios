package com.biblioteca.reserva_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaDTO {

    @NotNull
    @Positive
    private Long usuarioId;

    @NotNull
    @Positive
    private Long libroId;

    private LocalDate fechaReserva;
}