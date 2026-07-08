package com.biblioteca.reporte_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Table(name = "reportes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "tipo", nullable = false)
    private String tipo;


    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;


}
