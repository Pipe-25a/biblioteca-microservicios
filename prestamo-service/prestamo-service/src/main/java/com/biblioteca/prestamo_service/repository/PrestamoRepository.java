package com.biblioteca.prestamo_service.repository;

import com.biblioteca.prestamo_service.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    //buscar prestamos por usuarioId
    List<Prestamo> findByUsuarioId(Long usuarioId);
    //buscar prestamos por libroId
    List<Prestamo> findByLibroId(Long libroId); 

}
