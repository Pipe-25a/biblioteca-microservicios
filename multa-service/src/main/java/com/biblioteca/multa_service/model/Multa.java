package com.biblioteca.multa_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table(name = "multas")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "monto", nullable = false)
    private Double monto;
    @Column(name = "motivo", nullable = false)
    private String motivo;
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
}
