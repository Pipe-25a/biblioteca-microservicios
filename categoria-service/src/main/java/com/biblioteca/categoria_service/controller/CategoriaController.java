package com.biblioteca.categoria_service.controller;

import com.biblioteca.categoria_service.dto.CategoriaRequest;
import com.biblioteca.categoria_service.model.Categoria;
import com.biblioteca.categoria_service.service.CategoriaService;

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
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Gestión de Categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    //GET
    @GetMapping
    @Operation(summary = "Listar Categorias", description = "Obtiene una lista de todas las Categorias")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Categoria>> listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    //GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoria", description = "Retorna una categoria por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrado"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrado")
    })
    public ResponseEntity<Categoria>buscarCategoria(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.buscarCategoria(id));
    }

    //POST
    @PostMapping
    @Operation(summary = "Crear categoria", description = "Registra una nueva categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Categoria> guardarCategoria(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(request));
    }
    
    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoria", description = "Actualiza todos los campos de una categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrado")
    })
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id,
            @Valid @RequestBody CategoriaRequest dto) {
        log.info("Controller: Solicitud para actualizar categoria con ID: {}", id);
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }

    //DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrado")
    })
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoria eliminado");
    }
}
