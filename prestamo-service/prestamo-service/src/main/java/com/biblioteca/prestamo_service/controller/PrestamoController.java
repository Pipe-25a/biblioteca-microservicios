package com.biblioteca.prestamo_service.controller;

import com.biblioteca.prestamo_service.dto.PrestamoRequest;
import com.biblioteca.prestamo_service.dto.PrestamoResponse;
import com.biblioteca.prestamo_service.service.PrestamoService;

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
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "Gestión de Prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // GET - Listar todos
    @GetMapping
    @Operation(summary = "Listar Prestamos", description = "Obtiene una lista de todos los Prestamos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<PrestamoResponse>> listar() {
        log.info("Controller: Solicitud para listar todos los préstamos");
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener prestamo", description = "Retorna un prestamo por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prestamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<PrestamoResponse> buscar(@PathVariable Long id) {
        log.info("Controller: Buscando préstamo con ID: {}", id);
        return ResponseEntity.ok(prestamoService.buscarPrestamo(id));
    }

    // POST - Crear
    @PostMapping
    @Operation(summary = "Crear prestamo", description = "Registra un nuevo prestamo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prestamo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<PrestamoResponse> guardar(@Valid @RequestBody PrestamoRequest dto) {
        log.info("Controller: Solicitud para crear préstamo - Usuario: {}, Libro: {}",
                dto.getUsuarioId(), dto.getLibroId());
        PrestamoResponse nuevo = prestamoService.guardarPrestamo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT - Actualizar estado
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar prestamo", description = "Actualiza todos los campos de un prestamo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prestamo actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<PrestamoResponse> actualizarEstado(@PathVariable Long id,
                                                              @RequestParam String estado) {
        log.info("Controller: Actualizando estado del préstamo ID: {} a '{}'", id, estado);
        return ResponseEntity.ok(prestamoService.actualizarEstado(id, estado));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar prestamo", description = "Elimina un prestamo por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prestamo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.warn("Controller: Eliminando préstamo con ID: {}", id);
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.ok("Préstamo eliminado correctamente");
    }
}
