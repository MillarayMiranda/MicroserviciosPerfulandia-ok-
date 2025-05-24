package cl.duoc.inventario.repository;

import cl.duoc.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySucursalId(Long sucursalId);
    
    // Cambia a findByProductoId (sin el guión bajo)
    List<Inventario> findByProductoId(Long productoId);
    
    List<Inventario> findAll();
}