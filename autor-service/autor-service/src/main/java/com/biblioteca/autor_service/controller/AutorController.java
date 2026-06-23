package com.biblioteca.autor_service.controller;

import com.biblioteca.autor_service.dto.AutorRequest;
import com.biblioteca.autor_service.model.Autor;
import com.biblioteca.autor_service.service.AutorService;

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
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "Gestión de Autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    // GET - Listar todos
    @GetMapping
    @Operation(summary = "Listar Autores", description = "Obtiene una lista de todos los Autores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Autor>> listarAutores() {
        log.info("Controller: Solicitud para listar todos los autores");
        return ResponseEntity.ok(autorService.listarAutores());
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener autor", description = "Retorna un autor por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<Autor> buscarAutor(@PathVariable Long id) {
        log.info("Controller: Buscando autor con ID: {}", id);
        return ResponseEntity.ok(autorService.buscarAutor(id));
    }

    // POST - Crear
    @PostMapping
    @Operation(summary = "Crear autor", description = "Registra un nuevo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Autor> guardarAutor(@Valid @RequestBody AutorRequest dto) {
        log.info("Controller: Solicitud para crear autor: {}", dto.getNombre());
        Autor nuevo = autorService.guardarAutor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar autor", description = "Actualiza todos los campos de un autor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<Autor> actualizarAutor(@PathVariable Long id,
            @Valid @RequestBody AutorRequest dto) {
        log.info("Controller: Solicitud para actualizar autor con ID: {}", id);
        return ResponseEntity.ok(autorService.actualizarAutor(id, dto));
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar autor", description = "Elimina un autor por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<String> eliminarAutor(@PathVariable Long id) {
        log.warn("Controller: Eliminando autor con ID: {}", id);
        autorService.eliminarAutor(id);
        return ResponseEntity.ok("Autor eliminado correctamente");
    }
}
