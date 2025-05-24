package cl.duoc.producto.services;

import cl.duoc.producto.model.Producto;
import java.util.List;

public interface ProductoServices {
    Producto crearProducto(Producto producto);
    List<Producto> obtenerTodosProductos();
    Producto obtenerProductoPorId(Long id);
    Producto actualizarProducto(Long id, Producto producto);
    void eliminarProducto(Long id);
}