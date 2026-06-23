package com.biblioteca.autenticacion_service.repository;

import com.biblioteca.autenticacion_service.model.Autenticacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutenticacionRepository extends JpaRepository<Autenticacion, Long> {

    boolean existsByNombreUsuario(String nombreUsuario);

}
