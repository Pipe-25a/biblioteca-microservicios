package com.biblioteca.autenticacion_service.controller;

import com.biblioteca.autenticacion_service.dto.AutenticacionRequest;
import com.biblioteca.autenticacion_service.model.Autenticacion;
import com.biblioteca.autenticacion_service.service.AutenticacionService;

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
@RequestMapping("/api/autenticacion")
@Tag(name = "Autenticacion", description = "Servicio de Autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    // GET
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios disponibles")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Autenticacion>> listarUsuarios(){
        return ResponseEntity.ok(autenticacionService.listarUsuarios());
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Retorna un usuario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Autenticacion> buscarUsuario(@PathVariable Long id){
        return ResponseEntity.ok(autenticacionService.buscarUsuario(id));
    }

    // POST
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario ")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Autenticacion> guardarUsuario(@RequestBody Autenticacion autenticacion) {
        return ResponseEntity.ok(autenticacionService.guardarUsuario(autenticacion));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza nombre y contraseña del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nombre y contraseña del usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Autenticacion> actualizarUsuario(@PathVariable Long id,
            @Valid @RequestBody AutenticacionRequest dto) {
        log.info("Controller: Solicitud para actualizar autor con ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(autenticacionService.actualizarUsuario(id, dto));
    }


    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        autenticacionService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}