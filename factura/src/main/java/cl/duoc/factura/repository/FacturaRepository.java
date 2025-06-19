package cl.duoc.factura.repository;

import cl.duoc.factura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByUsuarioId(Long usuarioId);
}
