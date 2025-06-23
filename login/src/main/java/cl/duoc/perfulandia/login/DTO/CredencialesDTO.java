package cl.duoc.perfulandia.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredencialesDTO {

    private String correo;
    private String password;
    
}
