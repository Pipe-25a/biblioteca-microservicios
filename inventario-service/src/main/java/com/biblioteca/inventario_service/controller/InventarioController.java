package com.biblioteca.inventario_service.controller;

import com.biblioteca.inventario_service.dto.InventarioRequest;
import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Gestión de Inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // GET
    @GetMapping
    @Operation(summary = "Listar Inventario", description = "Obtiene una lista de todo el Inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Inventario>> listar(){
        return ResponseEntity.ok(inventarioService.listarInventario());
    }

    // GET BY ID
    @GetMapping("/{id}")
        @Operation(summary = "Obtener inventario", description = "Retorna un inventario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<Inventario> buscar(@PathVariable Long id){
        return ResponseEntity.ok(inventarioService.buscarInventario(id));
    }

    // POST
    @PostMapping
    @Operation(summary = "Crear inventario", description = "Registra un nuevo inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Inventario> guardar(@Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.ok(inventarioService.guardarInventario(request));
    }
    
    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza todos los campos de un inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id,
            @Valid @RequestBody InventarioRequest dto) {
        log.info("Controller: Solicitud para actualizar inventario con ID: {}", id);
        return ResponseEntity.ok(inventarioService.actualizarInventario(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un inventario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable Long id){inventarioService.eliminarInventario(id);
        return ResponseEntity.ok("Inventario eliminado");
    }
}