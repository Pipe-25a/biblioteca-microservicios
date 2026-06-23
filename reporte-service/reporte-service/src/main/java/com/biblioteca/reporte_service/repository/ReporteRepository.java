package com.biblioteca.reporte_service.repository;

import java.time.LocalDateTime;
import com.biblioteca.reporte_service.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    //Buscar reportes por tipo
    Reporte findByTipo(String tipo);
    //Buscar reportes por fecha de generación
    Reporte findByFechaGeneracion(LocalDateTime fechaGeneracion);
}
