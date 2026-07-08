package com.biblioteca.libro_service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
// DTO para representar la respuesta de un libro, con detalles como ID, título, autor, disponibilidad y fecha de creación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroResponse {
    private Long id;
    private String titulo;
    private String autor;
    private Long authorId;
    private Boolean disponible;

}
