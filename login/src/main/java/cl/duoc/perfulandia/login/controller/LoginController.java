package cl.duoc.perfulandia.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.perfulandia.login.model.Login;
import cl.duoc.perfulandia.login.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginservice;

    @PostMapping
    public ResponseEntity<?> Ingresar(@RequestBody Login loginIn){
        try {
            Login newLogin= loginservice.Ingresar(loginIn);
            return ResponseEntity.ok(newLogin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al ingresar credenciales");
        }
    }

    @GetMapping
    public ResponseEntity<?> Listar(){
        List<Login> login = loginservice.Listar();
        if(login.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran ingresos registrados");
        } else {
            return ResponseEntity.ok(login);
        }
    }

}
