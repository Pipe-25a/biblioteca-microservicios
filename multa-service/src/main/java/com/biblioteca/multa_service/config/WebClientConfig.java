package com.biblioteca.multa_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración de WebClient para comunicarse con el usuario-service.
 * Permite configurar la URL base del servicio de usuarios.
 */
@Configuration
public class WebClientConfig {

    @Value("${usuario-service.url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
