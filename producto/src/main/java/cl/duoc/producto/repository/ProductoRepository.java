package cl.duoc.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
}
