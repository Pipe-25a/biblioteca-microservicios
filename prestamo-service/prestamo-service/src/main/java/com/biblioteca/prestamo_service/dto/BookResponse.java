package com.biblioteca.prestamo_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// DTO para representar la respuesta de un libro, con detalles como ID, título, autor, disponibilidad y fecha de creación.
public class BookResponse {
    private Long id;
    private String titulo;
    private String autor;
    private boolean disponible;

}
