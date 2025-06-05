package cl.duoc.pedido.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PEDIDO")
@Data
@AllArgsConstructor
@NoArgsConstructor  


public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEDIDO_ID")
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Integer cliente_id;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "estado", nullable = false)
    private String estado;


}

