package com.biblioteca.multa_service.mapper;
import org.springframework.stereotype.Component;
import com.biblioteca.multa_service.dto.MultaRequest;
import com.biblioteca.multa_service.dto.MultaResponse;
import com.biblioteca.multa_service.model.Multa;
/**
 * Clase que proporciona métodos para mapear entre objetos de tipo Multa,
 * MultaRequest y MultaResponse.
 */
@Component
public class MultaMapper {
    // Request → Entity
    public Multa fromRequest(MultaRequest request) {
        return Multa.builder()
                // No se mapea el id: lo genera la base de datos automáticamente
                .monto(request.getMonto())
                .motivo(request.getMotivo())
                .usuarioId(request.getUsuarioId())
                .build();
    }
    // Entity → Response
    public MultaResponse toResponse(Multa multa) {
        return MultaResponse.builder()
                .id(multa.getId())          // ← incluye el id generado por la BD
                .monto(multa.getMonto())
                .motivo(multa.getMotivo())
                .usuarioId(multa.getUsuarioId())
                .build();
    }
}

