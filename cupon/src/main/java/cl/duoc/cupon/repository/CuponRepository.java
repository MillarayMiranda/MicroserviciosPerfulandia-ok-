package cl.duoc.cupon.repository;

import cl.duoc.cupon.model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
    
    // Buscar un cupón por su código
    Optional<Cupon> findByCodigo(String codigo);

    // Filtrar cupones por estado (por ejemplo: "ACTIVO", "USADO")
    java.util.List<Cupon> findByEstado(String estado);
}
