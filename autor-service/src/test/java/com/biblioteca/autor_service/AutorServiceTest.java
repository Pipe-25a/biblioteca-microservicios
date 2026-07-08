package com.biblioteca.autor_service;

import com.biblioteca.autor_service.dto.AutorRequest;
import com.biblioteca.autor_service.model.Autor;
import com.biblioteca.autor_service.repository.AutorRepository;
import com.biblioteca.autor_service.service.AutorService;
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
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    // ===================== listarAutores =====================

    @Test
    void listarAutores_debeRetornarListaDeAutores() {
        // Given
        Autor autor1 = new Autor(1L, "Gabriel García Márquez", "Colombiana");
        Autor autor2 = new Autor(2L, "Pablo Neruda", "Chilena");
        when(autorRepository.findAll()).thenReturn(List.of(autor1, autor2));

        // When
        List<Autor> resultado = autorService.listarAutores();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0).getNombre());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void listarAutores_cuandoNoHayAutores_debeRetornarListaVacia() {
        // Given
        when(autorRepository.findAll()).thenReturn(List.of());

        // When
        List<Autor> resultado = autorService.listarAutores();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(autorRepository, times(1)).findAll();
    }

    // ===================== guardarAutor =====================

    @Test
    void guardarAutor_conDatosValidos_debeRetornarAutorGuardado() {
        // Given
        AutorRequest request = new AutorRequest("Isabel Allende", "Chilena");
        Autor autorGuardado = new Autor(1L, "Isabel Allende", "Chilena");
        when(autorRepository.save(any(Autor.class))).thenReturn(autorGuardado);

        // When
        Autor resultado = autorService.guardarAutor(request);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Isabel Allende", resultado.getNombre());
        assertEquals("Chilena", resultado.getNacionalidad());
        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    // ===================== buscarAutor =====================

    @Test
    void buscarAutor_conIdExistente_debeRetornarAutor() {
        // Given
        Long id = 1L;
        Autor autor = new Autor(id, "Mario Vargas Llosa", "Peruana");
        when(autorRepository.findById(id)).thenReturn(Optional.of(autor));

        // When
        Autor resultado = autorService.buscarAutor(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Mario Vargas Llosa", resultado.getNombre());
        verify(autorRepository, times(1)).findById(id);
    }

    @Test
    void buscarAutor_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autorService.buscarAutor(id));
        assertTrue(ex.getMessage().contains("Autor no encontrado con ID: 99"));
        verify(autorRepository, times(1)).findById(id);
    }

    // ===================== actualizarAutor =====================

    @Test
    void actualizarAutor_conIdExistente_debeRetornarAutorActualizado() {
        // Given
        Long id = 1L;
        Autor autorExistente = new Autor(id, "Nombre Viejo", "Nacionalidad Vieja");
        AutorRequest request = new AutorRequest("Nombre Nuevo", "Chilena");
        Autor autorActualizado = new Autor(id, "Nombre Nuevo", "Chilena");
        when(autorRepository.findById(id)).thenReturn(Optional.of(autorExistente));
        when(autorRepository.save(any(Autor.class))).thenReturn(autorActualizado);

        // When
        Autor resultado = autorService.actualizarAutor(id, request);

        // Then
        assertNotNull(resultado);
        assertEquals("Nombre Nuevo", resultado.getNombre());
        assertEquals("Chilena", resultado.getNacionalidad());
        verify(autorRepository, times(1)).findById(id);
        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    @Test
    void actualizarAutor_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        AutorRequest request = new AutorRequest("Nombre", "Chilena");
        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> autorService.actualizarAutor(id, request));
        verify(autorRepository, never()).save(any());
    }

    // ===================== eliminarAutor =====================

    @Test
    void eliminarAutor_conIdValido_debeEjecutarsinErrores() {
        // Given
        Long id = 1L;
        doNothing().when(autorRepository).deleteById(id);

        // When
        autorService.eliminarAutor(id);

        // Then
        verify(autorRepository, times(1)).deleteById(id);
    }
}
