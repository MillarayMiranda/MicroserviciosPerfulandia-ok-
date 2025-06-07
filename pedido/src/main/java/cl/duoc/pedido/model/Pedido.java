package cl.duoc.pedido.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

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


public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEDIDO_ID")
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "estado", nullable = false)
    private String estado;
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setFecha(String string, DateTimeFormatter formatter) {
        // TODO Auto-generated method stub
        this.fecha = LocalDate.parse(string, formatter);
    }




}


