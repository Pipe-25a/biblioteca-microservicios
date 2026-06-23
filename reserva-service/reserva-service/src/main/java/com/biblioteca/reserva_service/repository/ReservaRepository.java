package com.biblioteca.reserva_service.repository;

import com.biblioteca.reserva_service.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    //buscar reservas por usuarioId
    Reserva findByUsuarioId(Long usuarioId);
    //buscar reservas por libroId
    Reserva findByLibroId(Long libroId);
}
