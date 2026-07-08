package com.biblioteca.categoria_service.service;

import com.biblioteca.categoria_service.dto.CategoriaRequest;
import com.biblioteca.categoria_service.model.Categoria;
import com.biblioteca.categoria_service.repository.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //LISTAR
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    //GUARDAR
    public Categoria guardarCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        return categoriaRepository.save(categoria);
    }

    //BUSCAR
    public Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }
    
    //ACTUALIZAR
    public Categoria actualizarCategoria(Long id, CategoriaRequest dto) {
        log.info("Service: Actualizando categoria con ID: {}", id);
        Categoria categoria = buscarCategoria(id);
        categoria.setNombre(dto.getNombre());
        Categoria actualizado = categoriaRepository.save(categoria);
        log.info("Service: Categoria actualizado correctamente con ID: {}", actualizado.getId());
        return actualizado;
    }

    //ELIMINAR
    public void eliminarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }
}
