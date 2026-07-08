package com.biblioteca.autenticacion_service.service;

import com.biblioteca.autenticacion_service.dto.AutenticacionRequest;
import com.biblioteca.autenticacion_service.model.Autenticacion;
import com.biblioteca.autenticacion_service.repository.AutenticacionRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    // LISTAR
    public List<Autenticacion> listarUsuarios(){
        return autenticacionRepository.findAll();
    }

    // GUARDAR
    public Autenticacion guardarUsuario(Autenticacion autenticacion){
        return autenticacionRepository.save(autenticacion);
    }

    // BUSCAR
    public Autenticacion buscarUsuario(Long id) {
        return autenticacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public Autenticacion actualizarUsuario(Long id, AutenticacionRequest dto) {
        log.info("Service: Actualizando usuario con ID: {}", id);
        Autenticacion autenticacion = buscarUsuario(id);
        autenticacion.setNombreUsuario(dto.getNombreUsuario());
        autenticacion.setPassword(dto.getPassword());
        Autenticacion actualizado = autenticacionRepository.save(autenticacion);
        log.info("Service: Autenticacion actualizado correctamente con ID: {}", actualizado.getId());
        return actualizado;
    }

    // ELIMINAR
    public void eliminarUsuario(Long id){
        autenticacionRepository.deleteById(id);
    }
}