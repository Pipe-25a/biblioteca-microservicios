package com.biblioteca.multa_service.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.multa_service.client.MultaClient;
import com.biblioteca.multa_service.dto.MultaRequest;
import com.biblioteca.multa_service.dto.MultaResponse;
import com.biblioteca.multa_service.mapper.MultaMapper;
import com.biblioteca.multa_service.model.Multa;
import com.biblioteca.multa_service.repository.MultaRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class MultaService {
    @Autowired
    private MultaRepository multaRepository;
    @Autowired
    private MultaMapper multaMapper;
    @Autowired
    private MultaClient multaClient;
    // LISTAR
    public List<MultaResponse> listarMultas() {
        log.info("Listando todas las multas");
        return multaRepository.findAll()
                .stream()
                .map(multaMapper::toResponse)
                .toList();
    }
    // GUARDAR
    public MultaResponse guardarMulta(MultaRequest request) {
            log.info("Validando usuario con ID: {}", request.getUsuarioId());
            multaClient.validarUsuario(request.getUsuarioId());
            Multa multa = multaMapper.fromRequest(request);
            Multa guardada = multaRepository.save(multa);
            log.info("Multa guardada con ID: {}", guardada.getId());
        return multaMapper.toResponse(guardada);
    }
    // BUSCAR
    public MultaResponse buscarMulta(Long id) {
            log.info("Buscando multa con ID: {}", id);
            Multa multa = multaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));
        return multaMapper.toResponse(multa);
    }
    // ACTUALIZAR
    public MultaResponse actualizarMulta(Long id, MultaRequest request) {
            log.info("Actualizando multa con ID: {}", id);
            Multa multa = multaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));
        multa.setMonto(request.getMonto());
        multa.setMotivo(request.getMotivo());
        multa.setUsuarioId(request.getUsuarioId());
        Multa actualizada = multaRepository.save(multa);
            log.info("Multa actualizada exitosamente con ID: {}", actualizada.getId());
        return multaMapper.toResponse(actualizada);
    }
    // ELIMINAR
    public void eliminarMulta(Long id) {
        log.info("Eliminando multa con ID: {}", id);
        multaRepository.deleteById(id);
    }
}
