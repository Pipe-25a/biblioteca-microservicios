package com.biblioteca.libro_service.service;

import com.biblioteca.libro_service.dto.LibroRequest;
import com.biblioteca.libro_service.model.Libro;
import com.biblioteca.libro_service.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    // LISTAR
    public List<Libro> listarLibros() {
        log.info("Service: Listando todos los libros");
        return libroRepository.findAll();
    }

    // GUARDAR
    public Libro guardarLibro(LibroRequest dto) {
        log.info("Service: Guardando libro con título: {}", dto.getTitulo());
        Libro libro = Libro.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .disponible(dto.getDisponible())
                .build();
        Libro guardado = libroRepository.save(libro);
        log.info("Service: Libro guardado con ID: {}", guardado.getId());
        return guardado;
    }

    // BUSCAR
    public Libro buscarLibroPorId(Long id) {
        log.info("Service: Buscando libro con ID: {}", id);
        return libroRepository.findById(id).orElseThrow(() -> {
            log.error("Service: Libro con ID {} no encontrado", id);
            return new RuntimeException("Libro no encontrado con ID: " + id);
        });
    }

    // ACTUALIZAR
    public Libro actualizarLibro(Long id, LibroRequest dto) {
        log.info("Service: Actualizando libro con ID: {}", id);
        Libro libro = buscarLibroPorId(id);
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setDisponible(dto.getDisponible());
        Libro actualizado = libroRepository.save(libro);
        log.info("Service: Libro actualizado correctamente con ID: {}", actualizado.getId());
        return actualizado;
    }

    // ELIMINAR
    public void eliminarLibro(Long id) {
        log.warn("Service: Eliminando libro con ID: {}", id);
        libroRepository.deleteById(id);
        log.info("Service: Libro con ID {} eliminado", id);
    }
    //buscar por autorId
    public List<Libro> buscarLibrosPorAutorId(Long authorId) {
        log.info("Service: Buscando libros con authorId: {}", authorId);
        return libroRepository.findByAuthorId(authorId);
    }
}
