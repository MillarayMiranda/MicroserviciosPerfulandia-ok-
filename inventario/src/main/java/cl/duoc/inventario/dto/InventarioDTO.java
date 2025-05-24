package cl.duoc.inventario.dto;

import jakarta.validation.constraints.*;

public class InventarioDTO {
    
    @Null(message = "El ID no debe ser proporcionado para creación")
    private Long id;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(0)
    @Max(9999)
    private Integer cantidad;

    @NotNull(message = "El ID de sucursal es obligatorio")
    @Positive
    private Long sucursalId;

    @NotNull(message = "El ID de producto es obligatorio")
    @Positive
    private Long productoId;

}