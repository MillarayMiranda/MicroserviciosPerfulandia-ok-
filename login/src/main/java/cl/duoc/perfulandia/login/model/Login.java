package cl.duoc.perfulandia.login.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="LOGIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Column(name = "mail", nullable = false)
    private String correo;
    
    @Column(name = "password", nullable = false)
    private String password;

}
