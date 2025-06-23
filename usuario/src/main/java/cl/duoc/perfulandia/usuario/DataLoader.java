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

            usuarionuevo.setRut(generarRutFalso());
            usuarionuevo.setNombre(obtenerNombreAleatorio());
            usuarionuevo.setCorreo(obtenerMailFalso(usuarionuevo.getNombre()));
            usuarionuevo.setPassword(faker.number().digits(6));
            usuarionuevo.setDireccion(faker.address().fullAddress());
            usuarionuevo.setTelefono(obtenerNumTelefonico());
            usuarionuevo.setIdrol(faker.number().numberBetween(1, 3));

            ventaservice.Guardar(usuarionuevo);
            System.out.println("Usuario guardado " + usuarionuevo.getRut());
        }
    }

    private String generarRutFalso() {
        int cuerpo =10000000 + random.nextInt(8999999);
        String dv =calcularDv(cuerpo);
        return cuerpo + "-" + dv;
    }

    private String calcularDv(int cuerpo) {
        int m= 0, s= 1;
        while (cuerpo !=0) {
            s = (s + cuerpo % 10 * (9 - m++ % 6)) % 11;
            cuerpo /= 10;
        }

        if (s == 0) return "K";
        if (s == 1) return "0";
        return String.valueOf(11 - s);
    }

    private String obtenerNombreAleatorio() {
        int option = random.nextInt(3);

        return switch (option){
            case 0 -> faker.breakingBad().character();
            case 1 -> faker.starWars().character();
            case 2 -> faker.gameOfThrones().character();
            default -> throw new IllegalStateException("Opcion obtenida invalida");
        };
    }

    private String obtenerMailFalso(String nombre) {
        String username = nombre.toLowerCase().replaceAll(" ", ".")
                                            .replaceAll("[^a-z.]", "");
        return username + "@gmail.com";
    }

    private String obtenerNumTelefonico() {
        String numero = faker.number().digits(8);
        return "+56 9 " + numero;
    }

}
