package com.biblioteca.usuario_service.repository;

import com.biblioteca.usuario_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //buscar por nombre
    Usuario findByNombre(String nombre);
    
}
