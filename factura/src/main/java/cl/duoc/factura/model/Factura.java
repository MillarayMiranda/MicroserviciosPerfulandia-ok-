package cl.duoc.factura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Table(name = "FACTURA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una factura en el sistema")
public class Factura {  // Ya no extiende RepresentationModel

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_seq")
    @SequenceGenerator(name = "factura_seq", sequenceName = "FACTURA_SEQ", allocationSize = 1)
    @Column(name = "idFactura")
    @Schema(description = "ID único de la factura", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "idPedido", nullable = false)
    @Schema(description = "ID del pedido asociado a la factura", example = "789")
    private Long pedidoId;

    @Column(name = "idUsuario", nullable = false)
    @Schema(description = "ID del usuario que generó la factura", example = "101")
    private Long usuarioId;

    @Column(name = "fecha", nullable = false)
    @Schema(description = "Fecha de emisión de la factura", example = "2023-12-15")
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    @Schema(description = "Monto total de la factura", example = "29990.99")
    private Double total;

    @Column(name = "metodoPago", nullable = false)
    @Schema(
        description = "Método de pago utilizado",
        example = "WEBPAY",
        allowableValues = {"WEBPAY", "TARJETA_CREDITO", "TRANSFERENCIA", "DEBITO"}
    )
    private String metodoPago;
}
