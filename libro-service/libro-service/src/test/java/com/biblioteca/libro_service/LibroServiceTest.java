package com.biblioteca.libro_service;

import com.biblioteca.libro_service.dto.LibroRequest;
import com.biblioteca.libro_service.model.Libro;
import com.biblioteca.libro_service.repository.LibroRepository;
import com.biblioteca.libro_service.service.LibroService;
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
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    // ===================== listarLibros =====================

    @Test
    void listarLibros_debeRetornarListaDeLibros() {
        // Given
        Libro libro1 = Libro.builder().id(1L).titulo("Cien Años de Soledad").autor("García Márquez").disponible(true).build();
        Libro libro2 = Libro.builder().id(2L).titulo("El Principito").autor("Saint-Exupéry").disponible(false).build();
        when(libroRepository.findAll()).thenReturn(List.of(libro1, libro2));

        // When
        List<Libro> resultado = libroService.listarLibros();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Cien Años de Soledad", resultado.get(0).getTitulo());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void listarLibros_cuandoNoHayLibros_debeRetornarListaVacia() {
        // Given
        when(libroRepository.findAll()).thenReturn(List.of());

        // When
        List<Libro> resultado = libroService.listarLibros();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarLibro =====================

    @Test
    void guardarLibro_conDatosValidos_debeRetornarLibroGuardado() {
        // Given
        LibroRequest request = LibroRequest.builder()
                .titulo("Don Quijote").autor("Cervantes").authorId(1L).disponible(true).build();
        Libro libroGuardado = Libro.builder()
                .id(1L).titulo("Don Quijote").autor("Cervantes").disponible(true).build();
        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);

        // When
        Libro resultado = libroService.guardarLibro(request);

        // Then
        assertNotNull(resultado);
        assertEquals("Don Quijote", resultado.getTitulo());
        assertEquals("Cervantes", resultado.getAutor());
        assertTrue(resultado.isDisponible());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    void guardarLibro_noDisponible_debeGuardarConDisponibleFalse() {
        // Given
        LibroRequest request = LibroRequest.builder()
                .titulo("Libro Prestado").autor("Autor").authorId(1L).disponible(false).build();
        Libro libroGuardado = Libro.builder()
                .id(1L).titulo("Libro Prestado").autor("Autor").disponible(false).build();
        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);

        // When
        Libro resultado = libroService.guardarLibro(request);

        // Then
        assertFalse(resultado.isDisponible());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    // ===================== buscarLibroPorId =====================

    @Test
    void buscarLibroPorId_conIdExistente_debeRetornarLibro() {
        // Given
        Long id = 1L;
        Libro libro = Libro.builder().id(id).titulo("Neruda Poemas").autor("Pablo Neruda").disponible(true).build();
        when(libroRepository.findById(id)).thenReturn(Optional.of(libro));

        // When
        Libro resultado = libroService.buscarLibroPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Neruda Poemas", resultado.getTitulo());
        verify(libroRepository, times(1)).findById(id);
    }

    @Test
    void buscarLibroPorId_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(libroRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> libroService.buscarLibroPorId(id));
        assertTrue(ex.getMessage().contains("Libro no encontrado con ID: 99"));
        verify(libroRepository, times(1)).findById(id);
    }

    // ===================== actualizarLibro =====================

    @Test
    void actualizarLibro_conIdExistente_debeRetornarLibroActualizado() {
        // Given
        Long id = 1L;
        Libro libroExistente = Libro.builder().id(id).titulo("Viejo Título").autor("Autor Viejo").disponible(true).build();
        LibroRequest request = LibroRequest.builder()
                .titulo("Nuevo Título").autor("Nuevo Autor").authorId(1L).disponible(false).build();
        Libro libroActualizado = Libro.builder().id(id).titulo("Nuevo Título").autor("Nuevo Autor").disponible(false).build();
        when(libroRepository.findById(id)).thenReturn(Optional.of(libroExistente));
        when(libroRepository.save(any(Libro.class))).thenReturn(libroActualizado);

        // When
        Libro resultado = libroService.actualizarLibro(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals("Nuevo Título", resultado.getTitulo());
        assertFalse(resultado.isDisponible());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    void actualizarLibro_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        LibroRequest request = LibroRequest.builder()
                .titulo("Título").autor("Autor").authorId(1L).disponible(true).build();
        when(libroRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> libroService.actualizarLibro(id, request));
        verify(libroRepository, never()).save(any());
    }

    // ===================== eliminarLibro =====================

    @Test
    void eliminarLibro_conIdValido_debeEliminarSinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(libroRepository).deleteById(id);

        // When
        libroService.eliminarLibro(id);

        // Then
        verify(libroRepository, times(1)).deleteById(id);
    }

    // ===================== buscarLibrosPorAutorId =====================

    @Test
    void buscarLibrosPorAutorId_conAutorExistente_debeRetornarSusLibros() {
        // Given
        Long autorId = 1L;
        Libro libro = Libro.builder().id(1L).titulo("Veinte Poemas").autor("Neruda").disponible(true).build();
        when(libroRepository.findByAuthorId(autorId)).thenReturn(List.of(libro));

        // When
        List<Libro> resultado = libroService.buscarLibrosPorAutorId(autorId);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Veinte Poemas", resultado.get(0).getTitulo());
        verify(libroRepository, times(1)).findByAuthorId(autorId);
    }
}
