package cl.duoc.cupon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "CUPON")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un cupón de descuento en el sistema")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cupon_seq")
    @SequenceGenerator(name = "cupon_seq", sequenceName = "CUPON_SEQ", allocationSize = 1)
    @Column(name = "idCupon")
    @Schema(description = "ID único autogenerado del cupón", 
           example = "1", 
           accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(min = 5, max = 20, message = "El código debe tener entre 5 y 20 caracteres")
    @Column(name = "codigo", nullable = false, unique = true)
    @Schema(description = "Código único identificador del cupón", 
           example = "VERANO2023",
           requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @NotNull(message = "El descuento es obligatorio")
    @DecimalMin(value = "1.0", message = "El descuento mínimo es 1%")
    @DecimalMax(value = "100.0", message = "El descuento máximo es 100%")
    @Column(name = "descuento", nullable = false)
    @Schema(description = "Porcentaje de descuento aplicable (1-100%)", 
           example = "15.5",
           requiredMode = Schema.RequiredMode.REQUIRED)
    private Double descuento;

    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado actual del cupón",
           example = "ACTIVO",
           allowableValues = {"ACTIVO", "INACTIVO", "USADO", "EXPIRADO"},
           requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;

    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación automática",
           example = "2023-12-01",
           accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fechaCreacion = LocalDate.now();

    @Future(message = "La fecha de expiración debe ser futura")
    @Column(name = "fecha_expiracion")
    @Schema(description = "Fecha de expiración del cupón",
           example = "2024-12-31")
    private LocalDate fechaExpiracion;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    @Column(name = "descripcion", length = 255)
    @Schema(description = "Descripción opcional del cupón",
           example = "Válido en productos seleccionados")
    private String descripcion;

    @PrePersist
    protected void onCreate() {
        if (this.estado == null) {
            this.estado = "ACTIVO"; // Valor por defecto
        }
    }
}