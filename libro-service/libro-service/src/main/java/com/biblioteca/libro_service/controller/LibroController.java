package com.biblioteca.libro_service.controller;

import com.biblioteca.libro_service.dto.LibroRequest;
import com.biblioteca.libro_service.model.Libro;
import com.biblioteca.libro_service.service.LibroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Gestión de Libros")
public class LibroController {
    // Inyección del servicio de libros para manejar la lógica de negocio
    @Autowired
    private LibroService libroService;

    // GET - Listar todos
    @GetMapping
    @Operation(summary = "Listar Libros", description = "Obtiene una lista de todos los Libros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Libro>> listarLibros() {
        log.info("Controller: Solicitud para listar todos los libros");
        return ResponseEntity.ok(libroService.listarLibros());
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro", description = "Retorna un libro por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<Libro> buscarLibro(@PathVariable Long id) {
        log.info("Controller: Buscando libro con ID: {}", id);
        return ResponseEntity.ok(libroService.buscarLibroPorId(id));
    }

    // POST - Crear
    @PostMapping
    @Operation(summary = "Crear libro", description = "Registra un nuevo libro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Libro> guardarLibro(@Valid @RequestBody LibroRequest dto) {
        log.info("Controller: Solicitud para crear libro: {}", dto.getTitulo());
        Libro nuevo = libroService.guardarLibro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza todos los campos de un libro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id,
                                                  @Valid @RequestBody LibroRequest dto) {
        log.info("Controller: Solicitud para actualizar libro con ID: {}", id);
        return ResponseEntity.ok(libroService.actualizarLibro(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina un libro por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<String> eliminarLibro(@PathVariable Long id) {
        log.warn("Controller: Eliminando libro con ID: {}", id);
        libroService.eliminarLibro(id);
        return ResponseEntity.ok("Libro eliminado correctamente");
    }
}
