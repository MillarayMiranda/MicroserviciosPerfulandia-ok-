package cl.duoc.perfulandia.usuario.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolUsuarioDTO {

    private Long rut;
    private String nombre;
    private String correo;
    private String password;
    private String nombreRol;

}
