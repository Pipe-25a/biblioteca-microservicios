package com.biblioteca.reserva_service.controller;

import com.biblioteca.reserva_service.dto.ReservaDTO;
import com.biblioteca.reserva_service.model.Reserva;
import com.biblioteca.reserva_service.service.ReservaService;

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
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Gestión de Reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET
    @GetMapping
        @Operation(summary = "Listar Reservas", description = "Obtiene una lista de todas las Reservas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Reserva>> listarReservas() {
        log.info("Controller: Solicitando lista de todas las reservas");
        return ResponseEntity.ok(reservaService.listarReservas());
    }
    // GET BY ID
    @GetMapping("/{id}")
        @Operation(summary = "Obtener reserva", description = "Retorna una reserva por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrado"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrado")
    })
    public ResponseEntity<Reserva> buscarReserva(@PathVariable Long id) {
        log.info("Controller: Buscando reserva con ID: {}", id);
        return ResponseEntity.ok(reservaService.buscarReserva(id));
    }

    // POST
    @PostMapping
    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        log.info("Controller: Iniciando creación de reserva para Usuario ID: {}", reservaDTO.getUsuarioId());
        Reserva nueva = reservaService.guardarReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva", description = "Actualiza todos los campos de una reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrado")
    })
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaDTO reservaDTO) {
        log.info("Controller: Petición para actualizar reserva con ID: {}", id);
        Reserva actualizada = reservaService.actualizarReserva(id, reservaDTO);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrado")
    })
    public ResponseEntity<String> eliminarReserva(@PathVariable Long id) {
        log.warn("Controller: Petición para eliminar reserva ID: {}", id);
        reservaService.eliminarReserva(id);
        return ResponseEntity.ok("Reserva eliminada con éxito");
    }
}