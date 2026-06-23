package com.biblioteca.multa_service.repository;
import com.biblioteca.multa_service.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {
    //metodo para regersar una lista de multas por usuarioId
    List<Multa> findByUsuarioId(Long usuarioId);
    //metodo para eliminar multas por usuarioId
    void deleteByUsuarioId(Long usuarioId);
    //metodo que regresa todas las multas existentes
    List<Multa> findAll();

}
