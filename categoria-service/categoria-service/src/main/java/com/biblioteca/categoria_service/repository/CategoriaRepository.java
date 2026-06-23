package com.biblioteca.categoria_service.repository;

import com.biblioteca.categoria_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    //buscar por nombre
    Categoria findByNombre(String nombre);
}
