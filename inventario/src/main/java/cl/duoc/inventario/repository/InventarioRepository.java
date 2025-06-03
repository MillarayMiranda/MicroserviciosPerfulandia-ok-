package cl.duoc.inventario.repository;

import cl.duoc.inventario.model.Inventario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySucursalId(Long sucursalId);
    List<Inventario> findByProductoId(Long productoId);

}