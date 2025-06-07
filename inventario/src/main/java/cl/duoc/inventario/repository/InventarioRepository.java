package cl.duoc.inventario.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySucursalId(Long sucursalId);
    Optional<Inventario> findByProductoId(Long productoId);
}