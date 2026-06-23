package com.biblioteca.prestamo_service.mapper;

import com.biblioteca.prestamo_service.dto.PrestamoRequest;
import com.biblioteca.prestamo_service.dto.PrestamoResponse;
import com.biblioteca.prestamo_service.model.Prestamo;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {
    // Convierte un PrestamoRequest a un Prestamo, asignando los campos correspondientes y estableciendo el estado por defecto a "ACTIVO" si no se proporciona.
    public Prestamo fromRequest(PrestamoRequest request) {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(request.getUsuarioId());
        prestamo.setLibroId(request.getLibroId());
        prestamo.setFechaPrestamo(request.getFechaPrestamo());
        prestamo.setEstado(request.getEstado() != null ? request.getEstado() : "ACTIVO");
        return prestamo;
    }
    // Convierte un Prestamo a un PrestamoResponse, asignando los campos correspondientes para la respuesta de la API.
    public PrestamoResponse toResponse(Prestamo prestamo) {
        return PrestamoResponse.builder()
                .id(prestamo.getId())
                .usuarioId(prestamo.getUsuarioId())
                .libroId(prestamo.getLibroId())
                .fechaPrestamo(prestamo.getFechaPrestamo())
                .estado(prestamo.getEstado())
                .build();
    }
}
