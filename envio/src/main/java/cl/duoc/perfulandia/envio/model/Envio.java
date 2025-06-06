package cl.duoc.perfulandia.envio.model;

import java.time.LocalDate;

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
@Table(name = "ENVIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEnvio")
    private Long id;

    @Column(name = "direccion")
    private String direccionEntrega;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha")
    private LocalDate fechaEnvio;

    @Column(name = "idPedido")
    private Long pedidoId;

}
