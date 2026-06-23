package com.biblioteca.multa_service.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

/**
 * Cliente HTTP para comunicarse con el usuario-service.
 * Permite validar si un usuario existe antes de registrar una multa.
 */
@Component
@Slf4j
public class MultaClient {

    @Autowired
    private WebClient webClient;
    /**
     * Valida que el usuario con el ID proporcionado exista en el usuario-service.
     * Realiza una solicitud GET a /usuarios/{id}.
     *
     * @param usuarioId Long - ID del usuario a validar
     * @throws NoSuchElementException si el usuario no existe (HTTP 404)
     * @throws RuntimeException       si ocurre cualquier otro error de comunicación
     */
    public void validarUsuario(Long usuarioId) {
        log.info("Validando existencia del usuario con ID: {}", usuarioId);
        try {
            webClient.get()
                    .uri("/usuarios/{id}", usuarioId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("Usuario con ID {} validado correctamente", usuarioId);
        } catch (WebClientResponseException ex) {
            log.error("Error al validar usuario con ID {}: {}", usuarioId, ex.getMessage());
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException(
                        "Usuario no encontrado con ID: " + usuarioId);
                default -> throw new RuntimeException(
                        "Error al comunicarse con usuario-service para ID: " + usuarioId, ex);
            }
        }
    }
}