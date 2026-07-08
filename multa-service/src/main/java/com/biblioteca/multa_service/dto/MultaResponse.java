package com.biblioteca.multa_service.dto;
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
// DTO para representar la respuesta de una multa, con detalles como ID, monto, motivo y el ID del usuario asociado.
public class MultaResponse {
    private Long id;
    private Double monto;
    private String motivo;
    private Long usuarioId;
}
