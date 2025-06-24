package cl.duoc.perfulandia.usuario.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.perfulandia.usuario.model.Usuario;
import cl.duoc.perfulandia.usuario.repository.UsuarioRepository;
import cl.duoc.perfulandia.usuario.services.UsuarioService;

public class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioservice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void BuscarUnUsuario(){
        Usuario u = new Usuario();
        u.setId(21L);
        u.setRut("20670459-4");
        u.setNombre("Diego Araya");
        u.setCorreo("correo@gmail.com");
        u.setPassword("12345678");
        u.setDireccion("direccion 1234, comuna");
        u.setTelefono("+56 9 91491146");
        u.setIdrol(1);
    }

    

}
