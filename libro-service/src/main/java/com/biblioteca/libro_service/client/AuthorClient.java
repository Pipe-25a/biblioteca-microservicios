package com.biblioteca.libro_service.client;

import com.biblioteca.libro_service.dto.AuthorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.NoSuchElementException;

/**
 * Cliente HTTP para comunicarse con el autor-service.
 * Permite obtener información de un autor por su ID.
 */
@Component
@Slf4j
public class AuthorClient {

    @Autowired
    private WebClient authorsWebClient;
    // Método para obtener un autor por su ID, maneja errores de comunicación y casos de autor no encontrado.
    /**
     * Obtiene la información de un autor por su ID.
     *
     * @param authorId Long - ID del autor
     * @return AuthorResponse con los datos del autor
     * @throws NoSuchElementException si el autor no existe (HTTP 404)
     * @throws RuntimeException si ocurre otro error de comunicación
     */
    public AuthorResponse getAuthorById(Long authorId) {
        log.info("Client: Consultando autor con ID: {}", authorId);
        try {
            return authorsWebClient.get()
                    .uri("/autores/{id}", authorId)
                    .retrieve()
                    .bodyToMono(AuthorResponse.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Client: Error al obtener autor con ID {}: {}", authorId, ex.getMessage());
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("Autor no encontrado con ID: " + authorId);
                default -> throw new RuntimeException("Error al comunicarse con autor-service para ID: " + authorId, ex);
            }
        }
    }
}
