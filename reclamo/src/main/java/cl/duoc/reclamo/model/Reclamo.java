package cl.duoc.reclamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "RECLAMO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un reclamo en el sistema")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reclamo_seq")
    @SequenceGenerator(name = "reclamo_seq", sequenceName = "RECLAMO_SEQ", allocationSize = 1)
    @Column(name = "idReclamo")
    @Schema(description = "ID único del reclamo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede exceder los 100 caracteres")
    @Column(name = "titulo", nullable = false, length = 100)
    @Schema(description = "Título breve del reclamo", example = "Problema con producto dañado")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(name = "descripcion", nullable = false, length = 500)
    @Schema(description = "Descripción detallada del reclamo", example = "El producto llegó con daños en el embalaje y partes faltantes")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    @Schema(description = "Fecha del reclamo", example = "2023-05-15")
    private LocalDate fecha;

    @NotNull(message = "El ID de usuario no puede ser nulo")
    @Column(name = "idUsuario", nullable = false)
    @Schema(description = "ID del usuario que realizó el reclamo", example = "12345")
    private Long usuarioId;

    @Column(name = "idProducto")
    @Schema(description = "ID del producto relacionado (si aplica)", example = "678", nullable = true)
    private Long idProducto;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    @Column(name = "comentarioResolucion", length = 500)
    @Schema(description = "Comentario de resolución del reclamo", 
            example = "Se envió producto de reemplazo", 
            nullable = true)
    private String comentarioResolucion;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDate.now();
        }
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setFecha(String string, DateTimeFormatter formatter) {
        this.fecha = LocalDate.parse(string, formatter);
    }
}
