package cl.duoc.factura.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {

    @Null(message = "El ID no debe ser enviado en la creación")
    private Long id;

    @NotNull(message = "El ID de pedido es obligatorio")
    @Positive(message = "El ID de pedido debe ser positivo")
    private Long pedidoId;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private Double total;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}
