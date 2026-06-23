package com.biblioteca.usuario_service.controller;

import com.biblioteca.usuario_service.dto.UsuarioDTO;
import com.biblioteca.usuario_service.model.Usuario;
import com.biblioteca.usuario_service.service.UsuarioService;

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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET
    @GetMapping
    @Operation(summary = "Listar Usuarios", description = "Obtiene una lista de todos los Usuarios")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        log.info("Controller: Solicitando lista de todos los usuarios");
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Retorna un usuario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){
        log.info("Controller: Buscando usuario con ID: {}", id);
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    // POST
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("Controller: Creando usuario con correo: {}", usuarioDTO.getCorreo());
        Usuario nuevo = usuarioService.guardarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza todos los campos de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("Controller: Petición para actualizar ID: {}", id);
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        log.warn("Controller: Eliminando usuario con ID: {}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}