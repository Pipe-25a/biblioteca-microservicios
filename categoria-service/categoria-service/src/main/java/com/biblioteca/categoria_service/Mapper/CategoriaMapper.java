package com.biblioteca.categoria_service.Mapper;

import org.springframework.stereotype.Component;

import com.biblioteca.categoria_service.model.Categoria;
import com.biblioteca.categoria_service.dto.CategoriaRequest;
import com.biblioteca.categoria_service.dto.CategoriaResponse;

@Component
public class CategoriaMapper {

    /**
     * Convierte un AuthorRequest a una entidad Author. Toma los campos del
     * AuthorRequest y los asigna a un nuevo objeto Author utilizando el patrón de
     * diseño Builder. El ID y la fecha de creación no se establecen en este método,
     * ya que se generarán automáticamente al persistir la entidad en la base de
     * datos. Este método se utiliza para mapear los datos de entrada recibidos en
     * las solicitudes HTTP a la entidad Author que se almacenará en la base de
     * datos.
     * 
     * @param request AuthorRequest - El objeto de solicitud que contiene los datos
     *                del nuevo autor a crear
     * @return Author - La entidad Author creada a partir del AuthorRequest
     */
    public Categoria toEntity(CategoriaRequest request) {
        return Categoria.builder()
                .nombre(request.getNombre())
                .build();
    }
        /**
     * Convierte una entidad Author a un AuthorResponse. Toma los campos de la
     * entidad Author y los asigna a un nuevo objeto AuthorResponse utilizando el
     * patrón de diseño Builder. Este método se utiliza para mapear los datos de la
     * entidad Author a un objeto de transferencia de datos que se devolverá en las
     * respuestas HTTP.
     * 
     * @param autor Author - La entidad Author que se desea convertir
     * @return AuthorResponse - El objeto de transferencia de datos creado a partir
     *         de la entidad Author
     */
    public CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())                
                .build();
    }


}
