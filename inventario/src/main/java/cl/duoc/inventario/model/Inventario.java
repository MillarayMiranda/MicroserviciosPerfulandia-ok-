package cl.duoc.inventario.model;

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
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTARIO_ID")
    private Long id;

    @Column(name = "CANTIDAD", nullable = false)
    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Max(value = 9999, message = "La cantidad no puede exceder 9999")
    private Integer cantidad;

    @Column(name = "SUCURSALID", nullable = false)
    @NotNull(message = "El ID de sucursal no puede ser nulo")
    @Positive(message = "El ID de sucursal debe ser positivo")
    private Long sucursalId;

    @Column(name = "PRODUCTO_ID", nullable = false)
    @NotNull(message = "El ID de producto no puede ser nulo")
    @Positive(message = "El ID de producto debe ser positivo")
    private Long productoId;
}