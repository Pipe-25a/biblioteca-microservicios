package com.biblioteca.reserva_service.service;

import com.biblioteca.reserva_service.dto.ReservaDTO;
import com.biblioteca.reserva_service.model.Reserva;
import com.biblioteca.reserva_service.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    // LISTAR
    public List<Reserva> listarReservas(){
        log.info("Service: Recuperando lista completa de reservas");
        return reservaRepository.findAll();
    }
    // GUARDAR
    public Reserva guardarReserva(ReservaDTO dto){
        log.info("Service: Mapeando DTO para nueva reserva - UsuarioID: {}, LibroID: {}",
                dto.getUsuarioId(), dto.getLibroId());
        Reserva reserva = new Reserva();
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setLibroId(dto.getLibroId());
        reserva.setFechaReserva(dto.getFechaReserva());
        Reserva guardada = reservaRepository.save(reserva);
        log.info("Service: Reserva guardada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }
    // BUSCAR
    public Reserva buscarReserva(Long id){
        log.info("Service: Buscando reserva con ID: {}", id);
        return reservaRepository.findById(id).orElseThrow(() -> {
                    log.error("Service: No se encontró la reserva con ID: {}", id);
                    return new RuntimeException("Reserva no encontrada");
                });}
    // ELIMINAR
    public void eliminarReserva(Long id){
        log.warn("Service: Intentando eliminar reserva con ID: {}", id);
        if(!reservaRepository.existsById(id)){
            log.error("Service: Error al eliminar. El ID {} no existe", id);
            throw new RuntimeException("Reserva no encontrada para eliminar");}
        reservaRepository.deleteById(id);
        log.info("Service: Reserva eliminada correctamente");
    }
    //ACTUALIZAR
    public Reserva actualizarReserva(Long id, ReservaDTO dto) {
        log.info("Service: Iniciando actualización de reserva ID: {}", id);
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setUsuarioId(dto.getUsuarioId());
            reserva.setLibroId(dto.getLibroId());
            reserva.setFechaReserva(dto.getFechaReserva());
            log.info("Service: Datos de reserva actualizados, guardando cambios...");
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> {
            log.error("Service: No se pudo actualizar. Reserva ID {} no encontrada", id);
            return new RuntimeException("Reserva no encontrada con ID: " + id);
        });
    }

}

