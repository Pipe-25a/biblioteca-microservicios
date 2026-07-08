package com.biblioteca.inventario_service;

import com.biblioteca.inventario_service.dto.InventarioRequest;
import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.repository.InventarioRepository;
import com.biblioteca.inventario_service.service.InventarioService;
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
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    // ===================== listarInventario =====================

    @Test
    void listarInventario_debeRetornarListaDeInventarios() {
        // Given
        Inventario i1 = new Inventario(1L, 1L, 10, "Estante A");
        Inventario i2 = new Inventario(2L, 2L, 5, "Estante B");
        when(inventarioRepository.findAll()).thenReturn(List.of(i1, i2));

        // When
        List<Inventario> resultado = inventarioService.listarInventario();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Estante A", resultado.get(0).getNombreInventario());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void listarInventario_cuandoNoHayRegistros_debeRetornarListaVacia() {
        // Given
        when(inventarioRepository.findAll()).thenReturn(List.of());

        // When
        List<Inventario> resultado = inventarioService.listarInventario();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarInventario =====================

    @Test
    void guardarInventario_conDatosValidos_debeRetornarInventarioGuardado() {
        // Given
        InventarioRequest request = new InventarioRequest(15, "Sección Novelas");
        Inventario guardado = new Inventario(1L, null, 15, "Sección Novelas");
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(guardado);

        // When
        Inventario resultado = inventarioService.guardarInventario(request);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(15, resultado.getStock());
        assertEquals("Sección Novelas", resultado.getNombreInventario());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void guardarInventario_conStockCero_debeGuardarCorrectamente() {
        // Given
        InventarioRequest request = new InventarioRequest(0, "Estante Vacío");
        Inventario guardado = new Inventario(1L, null, 0, "Estante Vacío");
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(guardado);

        // When
        Inventario resultado = inventarioService.guardarInventario(request);

        // Then
        assertNotNull(resultado);
        assertEquals(0, resultado.getStock());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    // ===================== buscarInventario =====================

    @Test
    void buscarInventario_conIdExistente_debeRetornarInventario() {
        // Given
        Long id = 1L;
        Inventario inventario = new Inventario(id, 1L, 20, "Estante Principal");
        when(inventarioRepository.findById(id)).thenReturn(Optional.of(inventario));

        // When
        Inventario resultado = inventarioService.buscarInventario(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(20, resultado.getStock());
        assertEquals("Estante Principal", resultado.getNombreInventario());
        verify(inventarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarInventario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> inventarioService.buscarInventario(id));
        assertEquals("Inventario no encontrado", ex.getMessage());
        verify(inventarioRepository, times(1)).findById(id);
    }

    // ===================== actualizarInventario =====================

    @Test
    void actualizarInventario_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Inventario existente = new Inventario(id, 1L, 5, "Estante Viejo");
        InventarioRequest request = new InventarioRequest(30, "Estante Nuevo");
        Inventario actualizado = new Inventario(id, 1L, 30, "Estante Nuevo");
        when(inventarioRepository.findById(id)).thenReturn(Optional.of(existente));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(actualizado);

        // When
        Inventario resultado = inventarioService.actualizarInventario(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals(30, resultado.getStock());
        assertEquals("Estante Nuevo", resultado.getNombreInventario());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void actualizarInventario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        InventarioRequest request = new InventarioRequest(10, "Estante");
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(id, request));
        verify(inventarioRepository, never()).save(any());
    }

    // ===================== eliminarInventario =====================

    @Test
    void eliminarInventario_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(inventarioRepository).deleteById(id);

        // When
        inventarioService.eliminarInventario(id);

        // Then
        verify(inventarioRepository, times(1)).deleteById(id);
    }
}
