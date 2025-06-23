package cl.duoc.perfulandia.envio.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Envio", description = "Modelo de Envio para Perfulandia")   

public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del Envio", example = "1")
    @Column(name = "idEnvio")
    private Long id;

    @Column(name = "direccion")
    @Schema(description = "Dirección de entrega del Envio", example = "Avenida Siempre Viva 123")
    private String direccionEntrega;

    @Column(name = "estado")
    @Schema(description = "Estado del Envio", example = "Enviado")
    private String estado;

    @Column(name = "fecha")
    @Schema(description = "Fecha de envío del Envio", example = "2023-10-01")
    private LocalDate fechaEnvio;

    @Column(name = "idPedido")
    @Schema(description = "ID del Pedido asociado al Envio", example = "1")
    private Long pedidoId;

}
