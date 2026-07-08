package com.biblioteca.autor_service.repository;

import com.biblioteca.autor_service.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//crud de autor
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    //Buscar por nombre
    Autor findByNombre(String nombre);
    //Buscar por nacionalidad
    Autor findByNacionalidad(String nacionalidad);
}
