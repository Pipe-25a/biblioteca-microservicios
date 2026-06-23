package com.biblioteca.multa_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// DTO para representar la solicitud de creación o actualización de una multa, con validaciones para los campos.
public class MultaRequest {
    
    @NotNull(message="El Monto no puede quedar en blanco y no puede ser menor a 0")
    private Double monto;

    @NotBlank(message="El Motivo no puede quedar en blanco")
    private String motivo;

    @NotNull(message="El ID de Usuario es obligatorio")
    private Long usuarioId;
}
