package com.biblioteca.reserva_service;

import com.biblioteca.reserva_service.dto.ReservaDTO;
import com.biblioteca.reserva_service.model.Reserva;
import com.biblioteca.reserva_service.repository.ReservaRepository;
import com.biblioteca.reserva_service.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    // ===================== listarReservas =====================

    @Test
    void listarReservas_debeRetornarListaDeReservas() {
        // Given
        Reserva r1 = new Reserva(1L, 1L, 1L, LocalDate.now());
        Reserva r2 = new Reserva(2L, 2L, 3L, LocalDate.now());
        when(reservaRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reserva> resultado = reservaService.listarReservas();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getUsuarioId());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void listarReservas_cuandoNoHayReservas_debeRetornarListaVacia() {
        // Given
        when(reservaRepository.findAll()).thenReturn(List.of());

        // When
        List<Reserva> resultado = reservaService.listarReservas();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarReserva =====================

    @Test
    void guardarReserva_conDatosValidos_debeRetornarReservaGuardada() {
        // Given
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLibroId(2L);
        dto.setFechaReserva(LocalDate.now());
        Reserva guardada = new Reserva(1L, 1L, 2L, LocalDate.now());
        when(reservaRepository.save(any(Reserva.class))).thenReturn(guardada);

        // When
        Reserva resultado = reservaService.guardarReserva(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals(2L, resultado.getLibroId());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    // ===================== buscarReserva =====================

    @Test
    void buscarReserva_conIdExistente_debeRetornarReserva() {
        // Given
        Long id = 1L;
        Reserva reserva = new Reserva(id, 1L, 1L, LocalDate.now());
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        // When
        Reserva resultado = reservaService.buscarReserva(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());
        verify(reservaRepository, times(1)).findById(id);
    }

    @Test
    void buscarReserva_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.buscarReserva(id));
        assertEquals("Reserva no encontrada", ex.getMessage());
        verify(reservaRepository, times(1)).findById(id);
    }

    // ===================== eliminarReserva =====================

    @Test
    void eliminarReserva_conIdExistente_debeEliminarCorrectamente() {
        // Given
        Long id = 1L;
        when(reservaRepository.existsById(id)).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(id);

        // When
        reservaService.eliminarReserva(id);

        // Then
        verify(reservaRepository, times(1)).existsById(id);
        verify(reservaRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarReserva_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(reservaRepository.existsById(id)).thenReturn(false);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.eliminarReserva(id));
        assertEquals("Reserva no encontrada para eliminar", ex.getMessage());
        verify(reservaRepository, never()).deleteById(any());
    }

    // ===================== actualizarReserva =====================

    @Test
    void actualizarReserva_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Reserva existente = new Reserva(id, 1L, 1L, LocalDate.now().minusDays(1));
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(2L);
        dto.setLibroId(3L);
        dto.setFechaReserva(LocalDate.now());
        Reserva actualizada = new Reserva(id, 2L, 3L, LocalDate.now());
        when(reservaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(actualizada);

        // When
        Reserva resultado = reservaService.actualizarReserva(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(2L, resultado.getUsuarioId());
        assertEquals(3L, resultado.getLibroId());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void actualizarReserva_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLibroId(1L);
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.actualizarReserva(id, dto));
        assertTrue(ex.getMessage().contains("Reserva no encontrada con ID: 99"));
        verify(reservaRepository, never()).save(any());
    }
}
