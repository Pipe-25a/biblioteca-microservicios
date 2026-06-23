package com.biblioteca.prestamo_service;

import com.biblioteca.prestamo_service.client.LibroClient;
import com.biblioteca.prestamo_service.client.UsuarioClient;
import com.biblioteca.prestamo_service.dto.PrestamoRequest;
import com.biblioteca.prestamo_service.dto.PrestamoResponse;
import com.biblioteca.prestamo_service.mapper.PrestamoMapper;
import com.biblioteca.prestamo_service.model.Prestamo;
import com.biblioteca.prestamo_service.repository.PrestamoRepository;
import com.biblioteca.prestamo_service.service.PrestamoService;
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
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private LibroClient libroClient;

    @Mock
    private PrestamoMapper prestamoMapper;

    @InjectMocks
    private PrestamoService prestamoService;

    // ===================== listarPrestamos =====================

    @Test
    void listarPrestamos_debeRetornarListaDePrestamos() {
        // Given
        Prestamo p1 = new Prestamo(1L, 1L, 1L, LocalDate.now(), "ACTIVO");
        PrestamoResponse resp1 = PrestamoResponse.builder()
                .id(1L).usuarioId(1L).libroId(1L)
                .fechaPrestamo(LocalDate.now()).estado("ACTIVO").build();
        when(prestamoRepository.findAll()).thenReturn(List.of(p1));
        when(prestamoMapper.toResponse(p1)).thenReturn(resp1);

        // When
        List<PrestamoResponse> resultado = prestamoService.listarPrestamos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ACTIVO", resultado.get(0).getEstado());
        verify(prestamoRepository, times(1)).findAll();
    }

    // ===================== guardarPrestamo =====================

    @Test
    void guardarPrestamo_conUsuarioYLibroValidos_debeGuardarCorrectamente() {
        // Given
        PrestamoRequest request = PrestamoRequest.builder()
                .usuarioId(1L).libroId(1L)
                .fechaPrestamo(LocalDate.now()).build();
        Prestamo prestamoMapeado = new Prestamo(null, 1L, 1L, LocalDate.now(), "ACTIVO");
        Prestamo prestamoGuardado = new Prestamo(1L, 1L, 1L, LocalDate.now(), "ACTIVO");
        PrestamoResponse response = PrestamoResponse.builder()
                .id(1L).usuarioId(1L).libroId(1L)
                .fechaPrestamo(LocalDate.now()).estado("ACTIVO").build();

        doNothing().when(usuarioClient).validarUsuario(1L);
        doNothing().when(libroClient).validarLibro(1L);
        when(prestamoMapper.fromRequest(request)).thenReturn(prestamoMapeado);
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamoGuardado);
        when(prestamoMapper.toResponse(prestamoGuardado)).thenReturn(response);

        // When
        PrestamoResponse resultado = prestamoService.guardarPrestamo(request);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("ACTIVO", resultado.getEstado());
        verify(usuarioClient, times(1)).validarUsuario(1L);
        verify(libroClient, times(1)).validarLibro(1L);
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    void guardarPrestamo_cuandoUsuarioNoExiste_debeLanzarExcepcion() {
        // Given
        PrestamoRequest request = PrestamoRequest.builder()
                .usuarioId(99L).libroId(1L)
                .fechaPrestamo(LocalDate.now()).build();
        doThrow(new RuntimeException("Usuario no encontrado con ID: 99"))
                .when(usuarioClient).validarUsuario(99L);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.guardarPrestamo(request));
        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    void guardarPrestamo_cuandoLibroNoExiste_debeLanzarExcepcion() {
        // Given
        PrestamoRequest request = PrestamoRequest.builder()
                .usuarioId(1L).libroId(99L)
                .fechaPrestamo(LocalDate.now()).build();
        doNothing().when(usuarioClient).validarUsuario(1L);
        doThrow(new RuntimeException("Libro no encontrado con ID: 99"))
                .when(libroClient).validarLibro(99L);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.guardarPrestamo(request));
        assertTrue(ex.getMessage().contains("Libro no encontrado"));
        verify(prestamoRepository, never()).save(any());
    }

    // ===================== buscarPrestamo =====================

    @Test
    void buscarPrestamo_conIdExistente_debeRetornarPrestamo() {
        // Given
        Long id = 1L;
        Prestamo prestamo = new Prestamo(id, 1L, 1L, LocalDate.now(), "ACTIVO");
        PrestamoResponse response = PrestamoResponse.builder()
                .id(id).estado("ACTIVO").build();
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));
        when(prestamoMapper.toResponse(prestamo)).thenReturn(response);

        // When
        PrestamoResponse resultado = prestamoService.buscarPrestamo(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("ACTIVO", resultado.getEstado());
    }

    @Test
    void buscarPrestamo_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.buscarPrestamo(id));
        assertTrue(ex.getMessage().contains("Préstamo no encontrado con ID: 99"));
    }

    // ===================== actualizarEstado =====================

    @Test
    void actualizarEstado_deACTIVOaDEVUELTO_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Prestamo prestamo = new Prestamo(id, 1L, 1L, LocalDate.now(), "ACTIVO");
        Prestamo prestamoActualizado = new Prestamo(id, 1L, 1L, LocalDate.now(), "DEVUELTO");
        PrestamoResponse response = PrestamoResponse.builder()
                .id(id).estado("DEVUELTO").build();
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamoActualizado);
        when(prestamoMapper.toResponse(prestamoActualizado)).thenReturn(response);

        // When
        PrestamoResponse resultado = prestamoService.actualizarEstado(id, "DEVUELTO");

        // Then
        assertNotNull(resultado);
        assertEquals("DEVUELTO", resultado.getEstado());
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    // ===================== eliminarPrestamo =====================

    @Test
    void eliminarPrestamo_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(prestamoRepository).deleteById(id);

        // When
        prestamoService.eliminarPrestamo(id);

        // Then
        verify(prestamoRepository, times(1)).deleteById(id);
    }
}
