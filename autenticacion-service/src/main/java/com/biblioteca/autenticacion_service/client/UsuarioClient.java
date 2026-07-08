package com.biblioteca.autenticacion_service.client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class UsuarioClient {
    @Autowired
    private WebClient webClient;

    public void validarUsuarioExistente(Long usuarioId) {
        log.info("Validando existencia de usuario con ID: {}", usuarioId);
        try{
            webClient.get()
                    .uri("/usuarios/{id}",usuarioId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("Usuario {} validado correctamente", usuarioId);
        
        } catch (WebClientResponseException ex) {
            log.error("Error validando usuario {}: {}", usuarioId, ex.getMessage());

            if (ex.getStatusCode().value() == 404) {
                throw new NoSuchElementException("Usuario no encontrado con ID: " + usuarioId);
            }
                throw new RuntimeException("Error comunicándose con usuario-service", ex);
        }
    }

}
