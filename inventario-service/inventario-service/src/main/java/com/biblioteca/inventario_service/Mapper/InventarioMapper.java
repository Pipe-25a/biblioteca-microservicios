package com.biblioteca.inventario_service.Mapper;
import org.springframework.stereotype.Component;
import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.dto.InventarioRequest;
import com.biblioteca.inventario_service.dto.InventarioResponse;

@Component
public class InventarioMapper {

    // Convierte un objeto AuthorRequest a un Author. Toma los campos del objeto AuthorRequest y los asigna a un nuevo objeto Author.
    public Inventario toEntity(InventarioRequest request) {
        return Inventario.builder()
                .stock(request.getStock())
                .nombreInventario(request.getNombreInventario())
                .build();
    }
    // Convierte un objeto Author a un AuthorResponse. Toma los campos del objeto
    public InventarioResponse toResponse(Inventario inventario) {
        return InventarioResponse.builder()
                .id(inventario.getId())
                .stock(inventario.getStock())
                .nombreInventario(inventario.getNombreInventario())                
                .build();
    }


}