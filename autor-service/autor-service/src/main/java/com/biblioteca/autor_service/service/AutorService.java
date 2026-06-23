package com.biblioteca.autor_service.service;

import com.biblioteca.autor_service.dto.AutorRequest;
import com.biblioteca.autor_service.model.Autor;
import com.biblioteca.autor_service.repository.AutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    // LISTAR
    public List<Autor> listarAutores() {
        log.info("Service: Consultando lista de todos los autores");
        return autorRepository.findAll();
    }

    // GUARDAR
    public Autor guardarAutor(AutorRequest dto) {
        log.info("Service: Guardando autor: {}", dto.getNombre());
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        Autor guardado = autorRepository.save(autor);
        log.info("Service: Autor guardado con ID: {}", guardado.getId());
        return guardado;
    }

    // BUSCAR
    public Autor buscarAutor(Long id) {
        log.info("Service: Buscando autor con ID: {}", id);
        return autorRepository.findById(id).orElseThrow(() -> {
            log.error("Service: Autor con ID {} no encontrado", id);
            return new RuntimeException("Autor no encontrado con ID: " + id);
        });
    }

    // ACTUALIZAR
    public Autor actualizarAutor(Long id, AutorRequest dto) {
        log.info("Service: Actualizando autor con ID: {}", id);
        Autor autor = buscarAutor(id);
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        Autor actualizado = autorRepository.save(autor);
        log.info("Service: Autor actualizado correctamente con ID: {}", actualizado.getId());
        return actualizado;
    }

    // ELIMINAR
    public void eliminarAutor(Long id) {
        log.warn("Service: Eliminando autor con ID: {}", id);
        autorRepository.deleteById(id);
        log.info("Service: Autor con ID {} eliminado", id);
    }
}
