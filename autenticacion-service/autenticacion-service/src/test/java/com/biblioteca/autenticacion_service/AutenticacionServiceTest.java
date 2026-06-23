package com.biblioteca.autenticacion_service;

import com.biblioteca.autenticacion_service.dto.AutenticacionRequest;
import com.biblioteca.autenticacion_service.model.Autenticacion;
import com.biblioteca.autenticacion_service.repository.AutenticacionRepository;
import com.biblioteca.autenticacion_service.service.AutenticacionService;
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
class AutenticacionServiceTest {

    @Mock
    private AutenticacionRepository autenticacionRepository;

    @InjectMocks
    private AutenticacionService autenticacionService;

    // ===================== listarUsuarios =====================

    @Test
    void listarUsuarios_debeRetornarListaDeUsuarios() {
        // Given
        Autenticacion a1 = new Autenticacion(1L, "admin", "pass123");
        Autenticacion a2 = new Autenticacion(2L, "usuario1", "clave456");
        when(autenticacionRepository.findAll()).thenReturn(List.of(a1, a2));

        // When
        List<Autenticacion> resultado = autenticacionService.listarUsuarios();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("admin", resultado.get(0).getNombreUsuario());
        verify(autenticacionRepository, times(1)).findAll();
    }

    @Test
    void listarUsuarios_cuandoNoHayRegistros_debeRetornarListaVacia() {
        // Given
        when(autenticacionRepository.findAll()).thenReturn(List.of());

        // When
        List<Autenticacion> resultado = autenticacionService.listarUsuarios();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarUsuario =====================

    @Test
    void guardarUsuario_conDatosValidos_debeRetornarUsuarioGuardado() {
        // Given
        Autenticacion autenticacion = new Autenticacion(null, "nuevoUsuario", "password123");
        Autenticacion guardado = new Autenticacion(1L, "nuevoUsuario", "password123");
        when(autenticacionRepository.save(any(Autenticacion.class))).thenReturn(guardado);

        // When
        Autenticacion resultado = autenticacionService.guardarUsuario(autenticacion);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("nuevoUsuario", resultado.getNombreUsuario());
        verify(autenticacionRepository, times(1)).save(any(Autenticacion.class));
    }

    // ===================== buscarUsuario =====================

    @Test
    void buscarUsuario_conIdExistente_debeRetornarUsuario() {
        // Given
        Long id = 1L;
        Autenticacion autenticacion = new Autenticacion(id, "admin", "pass123");
        when(autenticacionRepository.findById(id)).thenReturn(Optional.of(autenticacion));

        // When
        Autenticacion resultado = autenticacionService.buscarUsuario(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("admin", resultado.getNombreUsuario());
        verify(autenticacionRepository, times(1)).findById(id);
    }

    @Test
    void buscarUsuario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(autenticacionRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autenticacionService.buscarUsuario(id));
        assertEquals("Usuario no encontrado", ex.getMessage());
        verify(autenticacionRepository, times(1)).findById(id);
    }

    // ===================== actualizarUsuario =====================

    @Test
    void actualizarUsuario_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Autenticacion existente = new Autenticacion(id, "viejoNombre", "viejaPass");
        AutenticacionRequest request = new AutenticacionRequest("nuevoNombre", "nuevaPass123");
        Autenticacion actualizado = new Autenticacion(id, "nuevoNombre", "nuevaPass123");
        when(autenticacionRepository.findById(id)).thenReturn(Optional.of(existente));
        when(autenticacionRepository.save(any(Autenticacion.class))).thenReturn(actualizado);

        // When
        Autenticacion resultado = autenticacionService.actualizarUsuario(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals("nuevoNombre", resultado.getNombreUsuario());
        assertEquals("nuevaPass123", resultado.getPassword());
        verify(autenticacionRepository, times(1)).save(any(Autenticacion.class));
    }

    @Test
    void actualizarUsuario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        AutenticacionRequest request = new AutenticacionRequest("nombre", "pass123");
        when(autenticacionRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class,
                () -> autenticacionService.actualizarUsuario(id, request));
        verify(autenticacionRepository, never()).save(any());
    }

    // ===================== eliminarUsuario =====================

    @Test
    void eliminarUsuario_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(autenticacionRepository).deleteById(id);

        // When
        autenticacionService.eliminarUsuario(id);

        // Then
        verify(autenticacionRepository, times(1)).deleteById(id);
    }
}
