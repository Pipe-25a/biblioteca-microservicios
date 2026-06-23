package com.biblioteca.usuario_service.service;

import com.biblioteca.usuario_service.dto.UsuarioDTO;
import com.biblioteca.usuario_service.model.Usuario;
import com.biblioteca.usuario_service.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // LISTAR
    public List<Usuario> listarUsuarios(){
        log.info("Service: Consultando lista completa de usuarios");
        return usuarioRepository.findAll();
    }

    // GUARDAR
    public Usuario guardarUsuario(UsuarioDTO dto) {
        log.info("Service: Mapeando DTO a Entidad para guardar usuario: {}", dto.getCorreo());
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        return usuarioRepository.save(usuario);
    }

    // BUSCAR
    public Usuario buscarUsuarioPorId(Long id){
        log.info("Service: Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id).orElseThrow(() -> {
            log.error("Service: Usuario con ID {} no encontrado", id);
            return new RuntimeException("Usuario no encontrado");
        });
    }

    // ELIMINAR
    public void eliminarUsuario(Long id){
        log.warn("Service: Intentando eliminar usuario con ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            log.error("Service: No se puede eliminar. ID {} no existe", id);
            throw new RuntimeException("No se puede eliminar: Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
        log.info("Service: Usuario con ID {} eliminado correctamente", id);
    }

    // ACTUALIZAR
    public Usuario actualizarUsuario(Long id, UsuarioDTO dto) {
        log.info("Service: Iniciando actualización para el ID: {}", id);
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setTelefono(dto.getTelefono());
            log.info("Service: Datos actualizados en memoria, procediendo a guardar");
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> {
            log.error("Service: Error al actualizar. ID {} no encontrado", id);
            return new RuntimeException("Usuario no encontrado con ID: " + id);
        });
    }
}
