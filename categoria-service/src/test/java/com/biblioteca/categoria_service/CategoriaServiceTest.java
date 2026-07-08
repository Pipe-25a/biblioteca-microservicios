package com.biblioteca.categoria_service;

import com.biblioteca.categoria_service.dto.CategoriaRequest;
import com.biblioteca.categoria_service.model.Categoria;
import com.biblioteca.categoria_service.repository.CategoriaRepository;
import com.biblioteca.categoria_service.service.CategoriaService;
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
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    // ===================== listarCategorias =====================

    @Test
    void listarCategorias_debeRetornarListaDeCategorias() {
        // Given
        Categoria c1 = new Categoria(1L, "Ciencia Ficción");
        Categoria c2 = new Categoria(2L, "Historia");
        when(categoriaRepository.findAll()).thenReturn(List.of(c1, c2));

        // When
        List<Categoria> resultado = categoriaService.listarCategorias();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ciencia Ficción", resultado.get(0).getNombre());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void listarCategorias_cuandoNoHayCategorias_debeRetornarListaVacia() {
        // Given
        when(categoriaRepository.findAll()).thenReturn(List.of());

        // When
        List<Categoria> resultado = categoriaService.listarCategorias();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarCategoria =====================

    @Test
    void guardarCategoria_conDatosValidos_debeRetornarCategoriaGuardada() {
        // Given
        CategoriaRequest request = new CategoriaRequest("Novela");
        Categoria categoriaGuardada = new Categoria(1L, "Novela");
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaGuardada);

        // When
        Categoria resultado = categoriaService.guardarCategoria(request);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Novela", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    // ===================== buscarCategoria =====================

    @Test
    void buscarCategoria_conIdExistente_debeRetornarCategoria() {
        // Given
        Long id = 1L;
        Categoria categoria = new Categoria(id, "Poesía");
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        // When
        Categoria resultado = categoriaService.buscarCategoria(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Poesía", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void buscarCategoria_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> categoriaService.buscarCategoria(id));
        assertEquals("Categoria no encontrada", ex.getMessage());
        verify(categoriaRepository, times(1)).findById(id);
    }

    // ===================== actualizarCategoria =====================

    @Test
    void actualizarCategoria_conIdExistente_debeActualizarCorrectamente() {
        // Given
        Long id = 1L;
        Categoria existente = new Categoria(id, "Nombre Viejo");
        CategoriaRequest request = new CategoriaRequest("Nombre Nuevo");
        Categoria actualizada = new Categoria(id, "Nombre Nuevo");
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(actualizada);

        // When
        Categoria resultado = categoriaService.actualizarCategoria(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals("Nombre Nuevo", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void actualizarCategoria_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        CategoriaRequest request = new CategoriaRequest("Nombre");
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class,
                () -> categoriaService.actualizarCategoria(id, request));
        verify(categoriaRepository, never()).save(any());
    }

    // ===================== eliminarCategoria =====================

    @Test
    void eliminarCategoria_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(categoriaRepository).deleteById(id);

        // When
        categoriaService.eliminarCategoria(id);

        // Then
        verify(categoriaRepository, times(1)).deleteById(id);
    }
}
