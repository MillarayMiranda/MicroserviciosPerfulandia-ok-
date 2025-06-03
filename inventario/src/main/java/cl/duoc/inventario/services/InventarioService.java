package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;

import java.util.List;

public interface InventarioService {
    Inventario findById(Long id);
    List<Inventario> findBySucursalId(Long sucursalId);
    List<Inventario> findAll();
    Inventario findByProductoId(Long productoId);
    Inventario save(Inventario inventario);
    Inventario update(Long id, Inventario inventario);
    boolean delete(Long id);
    void crearInventario(Inventario nuevoInventario);
}

