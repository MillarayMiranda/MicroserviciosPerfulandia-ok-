package cl.duoc.factura.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FacturaUsuarioDTO {
    private Long idFactura;
    private LocalDate fecha;
    private Double total;
    private String nombreUsuario;
    private String rutUsuario;
    private String emailUsuario;
}
