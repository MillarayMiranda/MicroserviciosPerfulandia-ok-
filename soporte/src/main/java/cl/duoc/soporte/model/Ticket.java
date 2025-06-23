package cl.duoc.soporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "TICKETS_SOPORTE")
@Data
@Schema(description = "Modelo de Ticket de Soporte para Perfulandia")

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
    @SequenceGenerator(name = "ticket_seq", sequenceName = "TICKET_SEQ", allocationSize = 1)
    @Schema(description = "ID del Ticket", example = "1")
    @Column(name = "TICKET_ID")
    private Long id;

    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Email del Cliente", example = "ejemplo@ejemplo.com")
    @Email(message = "Debe ser un email válido")
    @Column(name = "EMAIL_CLIENTE", nullable = false)
    private String emailCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del Cliente", example = "Juan Pérez")
    @Column(name = "NOMBRE_CLIENTE", nullable = false)
    private String nombreCliente;

    @NotBlank(message = "El motivo es obligatorio")
    @Schema(description = "Motivo del Ticket", example = "Problema con el pedido")
    @Column(name = "MOTIVO", nullable = false, length = 500)
    private String motivo;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Schema(description = "Fecha de Creación del Ticket", example = "2023-10-15")
    private LocalDate fechaCreacion;

    @Column(name = "ESTADO", nullable = false)
    @Schema(description = "Estado del Ticket", example = "ABIERTO")
    private String estado = "ABIERTO"; // ABIERTO, EN_PROCESO, CERRADO

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaCreacion(String string, DateTimeFormatter formatter) {
        this.fechaCreacion = LocalDate.parse(string, formatter);
    }
}