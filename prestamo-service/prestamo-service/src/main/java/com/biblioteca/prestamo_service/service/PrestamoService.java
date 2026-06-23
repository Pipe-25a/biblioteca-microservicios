package com.biblioteca.prestamo_service.service;

import com.biblioteca.prestamo_service.client.LibroClient;
import com.biblioteca.prestamo_service.client.UsuarioClient;
import com.biblioteca.prestamo_service.dto.PrestamoRequest;
import com.biblioteca.prestamo_service.dto.PrestamoResponse;
import com.biblioteca.prestamo_service.mapper.PrestamoMapper;
import com.biblioteca.prestamo_service.model.Prestamo;
import com.biblioteca.prestamo_service.repository.PrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
// Servicio para gestionar la lógica de negocio relacionada con los préstamos, incluyendo validaciones, conversiones y operaciones CRUD.
@Slf4j
@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private LibroClient libroClient;

    @Autowired
    private PrestamoMapper prestamoMapper;

    // LISTAR
    public List<PrestamoResponse> listarPrestamos() {
        log.info("Service: Listando todos los préstamos");
        return prestamoRepository.findAll()
                .stream()
                .map(prestamoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GUARDAR
    public PrestamoResponse guardarPrestamo(PrestamoRequest dto) {
        log.info("Service: Validando usuario ID: {} y libro ID: {}", dto.getUsuarioId(), dto.getLibroId());
        usuarioClient.validarUsuario(dto.getUsuarioId());
        libroClient.validarLibro(dto.getLibroId());
        Prestamo prestamo = prestamoMapper.fromRequest(dto);
        prestamo.setEstado("ACTIVO");
        Prestamo guardado = prestamoRepository.save(prestamo);
        log.info("Service: Préstamo guardado con ID: {}", guardado.getId());
        return prestamoMapper.toResponse(guardado);
    }

    // BUSCAR
    public PrestamoResponse buscarPrestamo(Long id) {
        log.info("Service: Buscando préstamo con ID: {}", id);
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> {
            log.error("Service: Préstamo con ID {} no encontrado", id);
            return new RuntimeException("Préstamo no encontrado con ID: " + id);
        });
        return prestamoMapper.toResponse(prestamo);
    }

    // ACTUALIZAR ESTADO (ej: ACTIVO → DEVUELTO)
    public PrestamoResponse actualizarEstado(Long id, String nuevoEstado) {
        log.info("Service: Actualizando estado del préstamo ID: {} a '{}'", id, nuevoEstado);
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> {
            log.error("Service: Préstamo con ID {} no encontrado para actualizar", id);
            return new RuntimeException("Préstamo no encontrado con ID: " + id);
        });
        prestamo.setEstado(nuevoEstado);
        Prestamo actualizado = prestamoRepository.save(prestamo);
        log.info("Service: Estado actualizado correctamente para préstamo ID: {}", actualizado.getId());
        return prestamoMapper.toResponse(actualizado);
    }

    // ELIMINAR
    public void eliminarPrestamo(Long id) {
        log.warn("Service: Eliminando préstamo con ID: {}", id);
        prestamoRepository.deleteById(id);
        log.info("Service: Préstamo con ID {} eliminado", id);
    }
}
