package main.java.cl.duoc.soporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "TICKETS_SOPORTE")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
    @SequenceGenerator(name = "ticket_seq", sequenceName = "TICKET_SEQ", allocationSize = 1)
    @Column(name = "TICKET_ID")
    private Long id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Column(name = "EMAIL_CLIENTE", nullable = false)
    private String emailCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "NOMBRE_CLIENTE", nullable = false)
    private String nombreCliente;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(name = "MOTIVO", nullable = false, length = 500)
    private String motivo;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "ESTADO", nullable = false)
    private String estado = "ABIERTO"; // ABIERTO, EN_PROCESO, CERRADO
}