package cl.duoc.perfulandia.usuario;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.perfulandia.usuario.model.Usuario;
import cl.duoc.perfulandia.usuario.services.UsuarioService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));
    private final Random random = new Random();

    @Autowired
    private UsuarioService ventaservice;

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i < 10; i++) {
            Usuario usuarionuevo = new Usuario();
        }
    }

}
