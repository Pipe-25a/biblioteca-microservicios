package com.biblioteca.inventario_service.repository;

import com.biblioteca.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    //buscar por libroId
    Inventario findByLibroId(Long libroId);
    //buscar por nombreInventario
    Inventario findByNombreInventario(String nombreInventario);
    //buscar por stock
    Inventario findByStock(Integer stock);
}
