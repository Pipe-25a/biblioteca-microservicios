package com.biblioteca.multa_service;

import com.biblioteca.multa_service.client.MultaClient;
import com.biblioteca.multa_service.dto.MultaRequest;
import com.biblioteca.multa_service.dto.MultaResponse;
import com.biblioteca.multa_service.mapper.MultaMapper;
import com.biblioteca.multa_service.model.Multa;
import com.biblioteca.multa_service.repository.MultaRepository;
import com.biblioteca.multa_service.service.MultaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultaServiceTest {

    @Mock
    private MultaRepository multaRepository;

    @Mock
    private MultaMapper multaMapper;

    @Mock
    private MultaClient multaClient;

    @InjectMocks
    private MultaService multaService;

    // ===================== listarMultas =====================

    @Test
    void listarMultas_debeRetornarListaDeMultas() {
        // Given
        Multa multa1 = Multa.builder().id(1L).monto(5000.0).motivo("Atraso").usuarioId(1L).build();
        MultaResponse resp1 = MultaResponse.builder().id(1L).monto(5000.0).motivo("Atraso").usuarioId(1L).build();
        when(multaRepository.findAll()).thenReturn(List.of(multa1));
        when(multaMapper.toResponse(multa1)).thenReturn(resp1);

        // When
        List<MultaResponse> resultado = multaService.listarMultas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5000.0, resultado.get(0).getMonto());
        verify(multaRepository, times(1)).findAll();
    }

    @Test
    void listarMultas_cuandoNoHayMultas_debeRetornarListaVacia() {
        // Given
        when(multaRepository.findAll()).thenReturn(List.of());

        // When
        List<MultaResponse> resultado = multaService.listarMultas();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarMulta =====================

    @Test
    void guardarMulta_conUsuarioValido_debeGuardarCorrectamente() {
        // Given
        MultaRequest request = MultaRequest.builder()
                .monto(3000.0).motivo("Libro dañado").usuarioId(1L).build();
        Multa multaMapeada = Multa.builder().monto(3000.0).motivo("Libro dañado").usuarioId(1L).build();
        Multa multaGuardada = Multa.builder().id(1L).monto(3000.0).motivo("Libro dañado").usuarioId(1L).build();
        MultaResponse response = MultaResponse.builder().id(1L).monto(3000.0).motivo("Libro dañado").usuarioId(1L).build();

        doNothing().when(multaClient).validarUsuario(1L);
        when(multaMapper.fromRequest(request)).thenReturn(multaMapeada);
        when(multaRepository.save(any(Multa.class))).thenReturn(multaGuardada);
        when(multaMapper.toResponse(multaGuardada)).thenReturn(response);

        // When
        MultaResponse resultado = multaService.guardarMulta(request);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(3000.0, resultado.getMonto());
        assertEquals("Libro dañado", resultado.getMotivo());
        verify(multaClient, times(1)).validarUsuario(1L);
        verify(multaRepository, times(1)).save(any(Multa.class));
    }

    @Test
    void guardarMulta_cuandoUsuarioNoExiste_debeLanzarExcepcion() {
        // Given
        MultaRequest request = MultaRequest.builder()
                .monto(3000.0).motivo("Atraso").usuarioId(99L).build();
        doThrow(new RuntimeException("Usuario no encontrado con ID: 99"))
                .when(multaClient).validarUsuario(99L);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> multaService.guardarMulta(request));
        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
        verify(multaRepository, never()).save(any());
    }

    // ===================== buscarMulta =====================

    @Test
    void buscarMulta_conIdExistente_debeRetornarMulta() {
        // Given
        Long id = 1L;
        Multa multa = Multa.builder().id(id).monto(5000.0).motivo("Atraso").usuarioId(1L).build();
        MultaResponse response = MultaResponse.builder().id(id).monto(5000.0).motivo("Atraso").usuarioId(1L).build();
        when(multaRepository.findById(id)).thenReturn(Optional.of(multa));
        when(multaMapper.toResponse(multa)).thenReturn(response);

        // When
        MultaResponse resultado = multaService.buscarMulta(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(5000.0, resultado.getMonto());
    }

    @Test
    void buscarMulta_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(multaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> multaService.buscarMulta(id));
        assertTrue(ex.getMessage().contains("Multa no encontrada con ID: 99"));
    }

    // ===================== actualizarMulta =====================

    @Test
    void actualizarMulta_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Multa multaExistente = Multa.builder().id(id).monto(1000.0).motivo("Viejo").usuarioId(1L).build();
        MultaRequest request = MultaRequest.builder().monto(9000.0).motivo("Daño grave").usuarioId(1L).build();
        Multa multaActualizada = Multa.builder().id(id).monto(9000.0).motivo("Daño grave").usuarioId(1L).build();
        MultaResponse response = MultaResponse.builder().id(id).monto(9000.0).motivo("Daño grave").usuarioId(1L).build();
        when(multaRepository.findById(id)).thenReturn(Optional.of(multaExistente));
        when(multaRepository.save(any(Multa.class))).thenReturn(multaActualizada);
        when(multaMapper.toResponse(multaActualizada)).thenReturn(response);

        // When
        MultaResponse resultado = multaService.actualizarMulta(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals(9000.0, resultado.getMonto());
        assertEquals("Daño grave", resultado.getMotivo());
        verify(multaRepository, times(1)).save(any(Multa.class));
    }

    @Test
    void actualizarMulta_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        MultaRequest request = MultaRequest.builder().monto(1000.0).motivo("Test").usuarioId(1L).build();
        when(multaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> multaService.actualizarMulta(id, request));
        verify(multaRepository, never()).save(any());
    }

    // ===================== eliminarMulta =====================

    @Test
    void eliminarMulta_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(multaRepository).deleteById(id);

        // When
        multaService.eliminarMulta(id);

        // Then
        verify(multaRepository, times(1)).deleteById(id);
    }
}
