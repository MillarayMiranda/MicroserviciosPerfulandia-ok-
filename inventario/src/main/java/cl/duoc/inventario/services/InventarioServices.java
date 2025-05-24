package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import java.util.List;

public interface InventarioServices {
    Inventario findById(Long id);
    List<Inventario> findAll();
    List<Inventario> findBySucursalId(Long sucursalId);
    Inventario findByProductoId(Long productoId);
    Inventario save(Inventario inventario);
    Inventario update(Long id, Inventario inventario);
    default boolean delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}