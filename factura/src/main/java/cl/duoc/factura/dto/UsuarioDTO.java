package cl.duoc.factura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String rut;

    @JsonProperty("correo")
    private String email;
}
