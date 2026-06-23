package com.biblioteca.reporte_service.service;

import com.biblioteca.reporte_service.dto.ReporteDTO;
import com.biblioteca.reporte_service.model.Reporte;
import com.biblioteca.reporte_service.repository.ReporteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    // LISTAR
    public List<Reporte> listarReportes() {
        log.info("Service: Obteniendo todos los reportes de la base de datos");
        return reporteRepository.findAll();
    }

    // GUARDAR
    public Reporte guardarReporte(ReporteDTO dto) {
        log.info("Service: Creando reporte de tipo: {}", dto.getTipo());

        Reporte reporte = new Reporte();
        reporte.setTipo(dto.getTipo());
        reporte.setFechaGeneracion(LocalDateTime.now());

        return reporteRepository.save(reporte);
    }

    // BUSCAR
    public Reporte buscarReporte(Long id) {
        log.info("Service: Buscando reporte con ID: {}", id);
        return reporteRepository.findById(id).orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));
    }

    // ELIMINAR
    public void eliminarReporte(Long id) {
        log.warn("Service: Eliminando reporte ID: {}", id);
        reporteRepository.deleteById(id);
    }

    //ACTUALIZAR
    public Reporte actualizarReporte(Long id, ReporteDTO dto) {
        log.info("Service: Actualizando reporte ID: {}", id);
        return reporteRepository.findById(id).map(reporte -> {
            reporte.setTipo(dto.getTipo());
            reporte.setFechaGeneracion(LocalDateTime.now());
            return reporteRepository.save(reporte);
        }).orElseThrow(() -> new RuntimeException("No se encontró el reporte para actualizar"));
    }
}