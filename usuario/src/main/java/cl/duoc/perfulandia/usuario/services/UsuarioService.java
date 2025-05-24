package cl.duoc.perfulandia.usuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.perfulandia.usuario.DTO.RolDTO;
import cl.duoc.perfulandia.usuario.model.Usuario;
import cl.duoc.perfulandia.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final WebClient webclient;

    public UsuarioService(WebClient webclient) {
        this.webclient = webclient;
    }

    @Autowired
    private UsuarioRepository usuariorepository;

    @Value("${roles.url}")
    private String rolUrl;

    public RolDTO ConsultarRol(Integer idrol){
        try {
            RolDTO rol = webclient.get()
                                .uri("/{idrol}", idrol)
                                .retrieve()
                                .bodyToMono(RolDTO.class)
                                .block();
            return rol;
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar roles", e);
        }
    }

    public List<Usuario> BuscarUsuarios(){
        return usuariorepository.findAll();
    }

    public Usuario BuscarUnUsuario(Long rut){
        return usuariorepository.findById(rut).get();
    }

    public Usuario Guardar(Usuario usuario){
        RolDTO rol = ConsultarRol(usuario.getRol());

        if (rol == null) {
            throw new IllegalArgumentException("Rol especificado no existe");
        }

        return usuariorepository.save(usuario);
    }

    public void Eliminar(Long rut){
        usuariorepository.deleteById(rut);
    }

}
