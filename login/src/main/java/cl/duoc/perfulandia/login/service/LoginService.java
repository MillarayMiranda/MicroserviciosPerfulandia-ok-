package cl.duoc.perfulandia.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.duoc.perfulandia.login.DTO.CredencialesDTO;
import cl.duoc.perfulandia.login.model.Login;
import cl.duoc.perfulandia.login.repository.LoginRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoginService {

    private final WebClient webclient;

    public LoginService(WebClient webclient) {
        this.webclient = webclient;
    }

    @Autowired
    private LoginRepository loginrepository;

    public CredencialesDTO ConsultarCredenciales(String correo){
        try {
            CredencialesDTO credenciales = webclient.get()
                                                    .uri("/{correo}", correo)
                                                    .retrieve()
                                                    .bodyToMono(CredencialesDTO.class)
                                                    .block();
            return credenciales;
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar credenciales", e);
        }
    }

    public Login Ingresar(Login login){
        //CredencialesDTO credenciales = ConsultarCredenciales(login.getCorreo());
        return loginrepository.save(login);
        // if (credenciales != null
        //         && login.getCorreo().equals(credenciales.getCorreo())
        //         && login.getPassword().equals(credenciales.getPassword())) {
        //     return loginrepository.save(login);
        // } else {
        //     throw new IllegalArgumentException("Credenciales invalidas");
        // }
    }

    public List<Login> Listar(){
        return loginrepository.findAll();
    }

}