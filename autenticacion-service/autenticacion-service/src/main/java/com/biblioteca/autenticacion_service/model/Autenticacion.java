package com.biblioteca.autenticacion_service.model;

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
@Table(name = "autenticaciones")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autenticacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;
    @Column(name = "password", nullable = false)
    private String password;

}
