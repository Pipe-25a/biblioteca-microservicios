package com.biblioteca.multa_service.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.multa_service.dto.MultaRequest;
import com.biblioteca.multa_service.dto.MultaResponse;
import com.biblioteca.multa_service.service.MultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/multas")
@Tag(name = "Multas", description = "Gestión de Multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    //GET-Listar todas
    @GetMapping
    @Operation(summary = "Listar Multas", description = "Obtiene una lista de todas las Multas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<MultaResponse>> listarMultas() {
        log.info("Solicitud para listar todas las multas");
        List<MultaResponse> multas = multaService.listarMultas();
        log.info("Se encontraron {} multas", multas.size());
        return ResponseEntity.ok(multas);
    }
    
    //GET BY ID- buscar por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener multa", description = "Retorna un multa por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa encontrado"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrado")
    })
    public ResponseEntity<MultaResponse> buscarMulta(@PathVariable Long id) {
        log.info("Solicitud para buscar multa con ID: {}", id);
        MultaResponse multa = multaService.buscarMulta(id);
        log.info("Multa encontrada con ID: {}", multa.getId());
        return ResponseEntity.ok(multa);
    }
    
    //POST-Crear
    @PostMapping
    @Operation(summary = "Crear multa", description = "Registra una nueva multa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Multa creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<MultaResponse> guardarMulta(@Valid @RequestBody MultaRequest request) {
        log.info("Solicitud para crear nueva multa para usuario ID: {}", request.getUsuarioId());
        MultaResponse nuevaMulta = multaService.guardarMulta(request);
        log.info("Multa creada exitosamente con ID: {}", nuevaMulta.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMulta);
    }
    
    // PUT-Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar multa", description = "Actualiza todos los campos de un multa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrado")
    })
    public ResponseEntity<MultaResponse> actualizarMulta(
            @PathVariable Long id,
            @Valid @RequestBody MultaRequest request) {
        log.info("Solicitud para actualizar multa con ID: {}", id);
        return ResponseEntity.ok(multaService.actualizarMulta(id, request));
    }
    
    //DELETE-Borrar 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar multa", description = "Elimina un multa por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Multa eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrado")
    })
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long id) {
            log.info("Solicitud para eliminar multa con ID: {}", id);
            multaService.eliminarMulta(id);
            log.info("Multa con ID {} eliminada exitosamente", id);
        return ResponseEntity.noContent().build();
    }
}