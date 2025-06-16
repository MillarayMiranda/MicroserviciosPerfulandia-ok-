package cl.duoc.pedido.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name="PEDIDO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de Pedido para Perfulandia")  


public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del Pedido", example = "1")
    @Column(name = "PEDIDO_ID")
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    @Schema(description = "ID del Cliente que realizó el pedido", example = "123")
    private Integer clienteId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha del Pedido", example = "15-10-2023")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    @Schema(description = "Total del Pedido", example = "15000")
    private Double total;

    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado del Pedido", example = "Pendiente")
    private String estado;

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setFecha(String string, DateTimeFormatter formatter) {
        this.fecha = LocalDate.parse(string, formatter);
    }




}


