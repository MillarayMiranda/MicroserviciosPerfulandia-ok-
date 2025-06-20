package cl.duoc.perfulandia.usuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar roles", e);
        }
    }

    public List<Usuario> BuscarUsuarios(){
        return usuariorepository.findAll();
    }

    public Usuario BuscarUnUsuario(Long id){
        return usuariorepository.findById(id).get();
    }

    public Usuario Guardar(Usuario usuario){
        RolDTO rol = ConsultarRol(usuario.getIdrol());

        if (rol == null) {
            throw new IllegalArgumentException("Rol especificado no existe");
        } else {
            return usuariorepository.save(usuario);
        }
    }

    public void Eliminar(Long id){
        usuariorepository.deleteById(id);
    }

}
