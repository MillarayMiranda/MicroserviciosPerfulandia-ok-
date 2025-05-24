package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import java.util.List;

public interface InventarioServices {
    List<Inventario> findAll();
    List<Inventario> findBySucursalId(Long sucursalId);
    Inventario findByProductoId(Long productoId);
    Inventario save(Inventario inventario);
    Inventario update(Long id, Inventario inventario);
    static boolean delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}