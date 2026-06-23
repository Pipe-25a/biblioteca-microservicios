package com.biblioteca.prestamo_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder builder,
                         @Value("${usuario-service.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }

    public void validarUsuario(Long id) {
        log.info("Client: Validando existencia del usuario con ID: {}", id);
        try {
            webClient.get().uri("/usuarios/{id}", id).retrieve().bodyToMono(Object.class).block();
            log.info("Client: Usuario con ID {} validado correctamente", id);
        } catch (Exception e) {
            log.error("Client: Usuario con ID {} no encontrado o error: {}", id, e.getMessage());
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }
}