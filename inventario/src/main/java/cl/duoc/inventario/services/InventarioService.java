package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import java.util.List;

public interface InventarioService {
    Inventario findById(Long id);
    List<Inventario> findAll();
    List<Inventario> findBySucursalId(Long sucursalId);
    Inventario findByProductoId(Long productoId);
    Inventario save(Inventario inventario);
    Inventario update(Long id, Inventario inventario);
    boolean delete(Long id);
    Inventario crearInventario(Inventario inventario);
}
