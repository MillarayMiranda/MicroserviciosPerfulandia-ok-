package cl.duoc.perfulandia.usuario.service;

<<<<<<< HEAD
import org.mockito.Mock;

public class UsuarioTest {
=======
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

    

>>>>>>> 7d7be6f65d8b72a0d9751462da5f5ba1bba94231
}
