package com.biblioteca.reporte_service.controller;

import com.biblioteca.reporte_service.dto.ReporteDTO;
import com.biblioteca.reporte_service.model.Reporte;
import com.biblioteca.reporte_service.service.ReporteService;

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
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Gestión de Reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // GET
    @GetMapping
    @Operation(summary = "Listar Reportes", description = "Obtiene una lista de todos los Reportes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Reporte>> listarReportes() {
        log.info("Controller: Solicitud para listar reportes");
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    // GET
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte", description = "Retorna un reporte por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<Reporte> obtenerReporte(@PathVariable Long id) {
        log.info("Controller: Buscando reporte ID: {}", id);
        return ResponseEntity.ok(reporteService.buscarReporte(id));
    }

    // POST
    @PostMapping
        @Operation(summary = "Crear reporte", description = "Registra un nuevo reporte")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reporte creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Reporte> crearReporte(@Valid @RequestBody ReporteDTO reporteDTO) {
        log.info("Controller: Recibida solicitud para crear reporte tipo: {}", reporteDTO.getTipo());
        Reporte nuevo = reporteService.guardarReporte(reporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte", description = "Actualiza todos los campos de un reporte")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReporteDTO reporteDTO) {
        log.info("Controller: Solicitud de actualización para reporte ID: {}", id);
        return ResponseEntity.ok(reporteService.actualizarReporte(id, reporteDTO));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<String> eliminarReporte(@PathVariable Long id) {
        log.warn("Controller: Solicitud para eliminar reporte ID: {}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.ok("Reporte eliminado con éxito");
    }

}