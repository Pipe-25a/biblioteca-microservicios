package com.biblioteca.inventario_service.model;

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
@Builder
@Table(name = "inventarios")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "libro_id", nullable = false)
    private Long libroId;
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @Column(name = "nombre_inventario", nullable = false)
    private String nombreInventario;

}
