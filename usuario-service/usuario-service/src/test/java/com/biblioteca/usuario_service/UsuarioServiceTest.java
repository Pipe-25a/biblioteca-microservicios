package com.biblioteca.usuario_service;

import com.biblioteca.usuario_service.dto.UsuarioDTO;
import com.biblioteca.usuario_service.model.Usuario;
import com.biblioteca.usuario_service.repository.UsuarioRepository;
import com.biblioteca.usuario_service.service.UsuarioService;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // ===================== listarUsuarios =====================

    @Test
    void listarUsuarios_debeRetornarListaDeUsuarios() {
        // Given
        Usuario u1 = new Usuario(1L, "Ana González", "ana@mail.cl", "912345678");
        Usuario u2 = new Usuario(2L, "Pedro Soto", "pedro@mail.cl", "987654321");
        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));

        // When
        List<Usuario> resultado = usuarioService.listarUsuarios();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ana González", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void listarUsuarios_cuandoNoHayUsuarios_debeRetornarListaVacia() {
        // Given
        when(usuarioRepository.findAll()).thenReturn(List.of());

        // When
        List<Usuario> resultado = usuarioService.listarUsuarios();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ===================== guardarUsuario =====================

    @Test
    void guardarUsuario_conDatosValidos_debeRetornarUsuarioGuardado() {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .nombre("Carlos López")
                .correo("carlos@mail.cl")
                .telefono("911111111")
                .build();
        Usuario usuarioGuardado = new Usuario(1L, "Carlos López", "carlos@mail.cl", "911111111");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // When
        Usuario resultado = usuarioService.guardarUsuario(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Carlos López", resultado.getNombre());
        assertEquals("carlos@mail.cl", resultado.getCorreo());
        assertEquals("911111111", resultado.getTelefono());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // ===================== buscarUsuarioPorId =====================

    @Test
    void buscarUsuarioPorId_conIdExistente_debeRetornarUsuario() {
        // Given
        Long id = 1L;
        Usuario usuario = new Usuario(id, "María Pérez", "maria@mail.cl", "922222222");
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // When
        Usuario resultado = usuarioService.buscarUsuarioPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("María Pérez", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarUsuarioPorId_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.buscarUsuarioPorId(id));
        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
        verify(usuarioRepository, times(1)).findById(id);
    }

    // ===================== eliminarUsuario =====================

    @Test
    void eliminarUsuario_conIdExistente_debeEliminarCorrectamente() {
        // Given
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(id);

        // When
        usuarioService.eliminarUsuario(id);

        // Then
        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarUsuario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.eliminarUsuario(id));
        assertTrue(ex.getMessage().contains("No se puede eliminar"));
        verify(usuarioRepository, never()).deleteById(any());
    }

    // ===================== actualizarUsuario =====================

    @Test
    void actualizarUsuario_conIdExistente_debeRetornarUsuarioActualizado() {
        // Given
        Long id = 1L;
        Usuario usuarioExistente = new Usuario(id, "Viejo Nombre", "viejo@mail.cl", "900000000");
        UsuarioDTO dto = UsuarioDTO.builder()
                .nombre("Nuevo Nombre")
                .correo("nuevo@mail.cl")
                .telefono("933333333")
                .build();
        Usuario usuarioActualizado = new Usuario(id, "Nuevo Nombre", "nuevo@mail.cl", "933333333");
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // When
        Usuario resultado = usuarioService.actualizarUsuario(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombre());
        assertEquals("nuevo@mail.cl", resultado.getCorreo());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void actualizarUsuario_conIdInexistente_debeLanzarExcepcion() {
        // Given
        Long id = 99L;
        UsuarioDTO dto = UsuarioDTO.builder()
                .nombre("Nombre")
                .correo("test@mail.cl")
                .telefono("900000000")
                .build();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(id, dto));
        verify(usuarioRepository, never()).save(any());
    }
}
