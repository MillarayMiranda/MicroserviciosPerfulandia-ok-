package cl.duoc.inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "INVENTARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag (name = "Inventario", description = "Modelo de Inventario para Perfulandia")

public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del Inventario", example = "1")
    @Column(name = "INVENTARIO_ID")
    private Long id;

    @Column(name = "CANTIDAD", nullable = false)
    @Schema(description = "Cantidad de producto en inventario", example = "100")
    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Max(value = 9999, message = "La cantidad no puede exceder 9999")
    private Integer cantidad;

    @Column(name = "SUCURSALID", nullable = false)
    @Schema(description = "ID de la sucursal donde se encuentra el inventario", example = "1")
    @NotNull(message = "El ID de sucursal no puede ser nulo")
    @Positive(message = "El ID de sucursal debe ser positivo")
    private Long sucursalId;

    @Column(name = "PRODUCTO_ID", nullable = false)
    @Schema(description = "ID del producto asociado al inventario", example = "1")
    @NotNull(message = "El ID de producto no puede ser nulo")
    @Positive(message = "El ID de producto debe ser positivo")
    private Long productoId;
}