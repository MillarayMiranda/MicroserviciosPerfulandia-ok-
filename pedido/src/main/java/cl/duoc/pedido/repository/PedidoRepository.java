package cl.duoc.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario

    
} 
