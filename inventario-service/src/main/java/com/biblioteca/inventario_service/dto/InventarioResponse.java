package com.biblioteca.inventario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioResponse {
    private Long id;
    private Integer stock;
    private String nombreInventario;
}