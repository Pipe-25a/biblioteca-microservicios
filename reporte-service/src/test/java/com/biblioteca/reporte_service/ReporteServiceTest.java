package com.biblioteca.reporte_service;

import com.biblioteca.reporte_service.dto.ReporteDTO;
import com.biblioteca.reporte_service.model.Reporte;
import com.biblioteca.reporte_service.repository.ReporteRepository;
import com.biblioteca.reporte_service.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    // ===================== listarReportes =====================

    @Test
    void listarReportes_debeRetornarListaDeReportes() {
        // Given
        Reporte r1 = new Reporte(1L, "PRESTAMOS", LocalDateTime.now());
        Reporte r2 = new Reporte(2L, "MULTAS", LocalDateTime.now());
        when(reporteRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reporte> resultado = reporteService.listarReportes();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("PRESTAMOS", resultado.get(0).getTipo());
        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    void listarReportes_cuandoNoHayReportes_debeRetornarListaVacia() {
        // Given
        when(reporteRepository.findAll()).thenReturn(List.of());

        // When
        List<Reporte> resultado = reporteService.listarReportes();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarReporte =====================

    @Test
    void guardarReporte_conDatosValidos_debeRetornarReporteGuardado() {
        // Given
        ReporteDTO dto = new ReporteDTO();
        dto.setTipo("INVENTARIO");
        Reporte guardado = new Reporte(1L, "INVENTARIO", LocalDateTime.now());
        when(reporteRepository.save(any(Reporte.class))).thenReturn(guardado);

        // When
        Reporte resultado = reporteService.guardarReporte(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("INVENTARIO", resultado.getTipo());
        assertNotNull(resultado.getFechaGeneracion());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    // ===================== buscarReporte =====================

    @Test
    void buscarReporte_conIdExistente_debeRetornarReporte() {
        // Given
        Long id = 1L;
        Reporte reporte = new Reporte(id, "RESERVAS", LocalDateTime.now());
        when(reporteRepository.findById(id)).thenReturn(Optional.of(reporte));

        // When
        Reporte resultado = reporteService.buscarReporte(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("RESERVAS", resultado.getTipo());
        verify(reporteRepository, times(1)).findById(id);
    }

    @Test
    void buscarReporte_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(reporteRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reporteService.buscarReporte(id));
        assertTrue(ex.getMessage().contains("Reporte no encontrado con ID: 99"));
        verify(reporteRepository, times(1)).findById(id);
    }

    // ===================== actualizarReporte =====================

    @Test
    void actualizarReporte_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Reporte existente = new Reporte(id, "TIPO VIEJO", LocalDateTime.now().minusDays(1));
        ReporteDTO dto = new ReporteDTO();
        dto.setTipo("TIPO NUEVO");
        Reporte actualizado = new Reporte(id, "TIPO NUEVO", LocalDateTime.now());
        when(reporteRepository.findById(id)).thenReturn(Optional.of(existente));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(actualizado);

        // When
        Reporte resultado = reporteService.actualizarReporte(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals("TIPO NUEVO", resultado.getTipo());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    void actualizarReporte_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        ReporteDTO dto = new ReporteDTO();
        dto.setTipo("TIPO");
        when(reporteRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reporteService.actualizarReporte(id, dto));
        assertTrue(ex.getMessage().contains("No se encontró el reporte para actualizar"));
        verify(reporteRepository, never()).save(any());
    }

    // ===================== eliminarReporte =====================

    @Test
    void eliminarReporte_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(reporteRepository).deleteById(id);

        // When
        reporteService.eliminarReporte(id);

        // Then
        verify(reporteRepository, times(1)).deleteById(id);
    }
}
