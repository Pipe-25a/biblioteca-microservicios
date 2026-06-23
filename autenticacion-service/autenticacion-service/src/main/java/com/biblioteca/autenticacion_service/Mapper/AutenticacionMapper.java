package com.biblioteca.autenticacion_service.Mapper;

import org.springframework.stereotype.Component;
import com.biblioteca.autenticacion_service.dto.AutenticacionRequest;
import com.biblioteca.autenticacion_service.dto.AutenticacionResponse;
import com.biblioteca.autenticacion_service.model.Autenticacion;

@Component
public class AutenticacionMapper {
// Mapper para convertir entre DTOs y entidades de autenticación
    /**
     * Convierte un DTO de solicitud de autenticación en una entidad Autenticacion para persistir en la BD.
     *
     * @param request DTO con los datos enviados por el cliente
     * @return Entidad Autenticacion lista para ser guardada (incluye contraseña)
     */
    public Autenticacion fromRequest(AutenticacionRequest request) {
        Autenticacion autenticacion = new Autenticacion();
        autenticacion.setNombreUsuario(request.getNombreUsuario());
        autenticacion.setPassword(request.getPassword());
        return autenticacion;
    }

    /**
     * Convierte una entidad Autenticacion en un DTO AutenticacionResponse para enviar al cliente.
     *
     * @param autenticacion Entidad recuperada de la BD
     * @return DTO con los datos que se pueden exponer al cliente (sin contraseña)
     */
    public AutenticacionResponse toResponse(Autenticacion autenticacion) {
        return AutenticacionResponse.builder()
                .id(autenticacion.getId())
                .nombreUsuario(autenticacion.getNombreUsuario())
                // password omitido a propósito
                .build();
    }
}