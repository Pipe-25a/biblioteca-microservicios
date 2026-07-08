package com.biblioteca.autor_service.client;

import com.biblioteca.autor_service.dto.LibroResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

/**
 * Cliente HTTP para consumir datos del propio autor-service.
 */
@Component
@Slf4j
public class LibroClient { // Cambiado de nombre

    @Autowired
    private WebClient librosWebClient; // Apunta a libro-service

    public List<LibroResponse> getLibrosByAutor(Long autorId) {
        log.info("Client: Consultando libros del autor {}", autorId);
        return librosWebClient.get()
                .uri("/api/libros/autor/{id}", autorId) // Endpoint de libro-service
                .retrieve()
                .bodyToFlux(LibroResponse.class)
                .collectList()
                .block();
    }
}