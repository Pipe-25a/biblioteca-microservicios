package com.biblioteca.categoria_service.client;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.biblioteca.categoria_service.dto.CategoriaResponse;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Component
@Slf4j
public class CategoriaClient {

    @Autowired
    private WebClient categoriasWebClient;

    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría a buscar
     * @return CategoriaResponse - La categoría encontrada
     */
    public CategoriaResponse getCategoriaById(Long id) {  
        log.info("Obteniendo categoría con ID {}", id);
        try {
            return categoriasWebClient.get()
                    .uri("/categorias/{id}", id)  
                    .retrieve()
                    .bodyToMono(CategoriaResponse.class)  
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error al obtener la categoría con ID {}", id, ex);
            if (ex.getStatusCode().value() == 404) {
                throw new NoSuchElementException("No se encontró la categoría con ID: " + id);
            }
            throw new RuntimeException("Error al obtener la categoría con ID " + id, ex);
        }
    }

    /**
     * Obtiene todas las categorías
     * @return List<CategoriaResponse> - Lista de todas las categorías
     */
    public List<CategoriaResponse> getAllCategorias() {
        log.info("Obteniendo todas las categorías");
        try {
            return categoriasWebClient.get()
                    .uri("/categorias")
                    .retrieve()
                    .bodyToFlux(CategoriaResponse.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error al obtener todas las categorías", ex);
            throw new RuntimeException("Error al obtener todas las categorías", ex);
        }
    }
}