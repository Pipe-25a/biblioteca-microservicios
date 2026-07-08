package com.biblioteca.reserva_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Table(name = "reservas")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "usuario_id", nullable = false)

    private Long usuarioId;


    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;
}
