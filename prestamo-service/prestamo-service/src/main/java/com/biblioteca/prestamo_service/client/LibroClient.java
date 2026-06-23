package com.biblioteca.prestamo_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class LibroClient {

    private final WebClient webClient;

    public LibroClient(WebClient.Builder builder, @Value("${libro-service.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }

    public void validarLibro(Long id) {
        log.info("Client: Validando existencia del libro con ID: {}", id);
        try {
            webClient.get().uri("/libros/{id}", id).retrieve().bodyToMono(Object.class).block();
            log.info("Client: Libro con ID {} validado correctamente", id);
        } catch (Exception e) {
            log.error("Client: Libro con ID {} no encontrado o error: {}", id, e.getMessage());
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
    }
}