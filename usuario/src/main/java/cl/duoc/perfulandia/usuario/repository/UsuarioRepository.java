package cl.duoc.perfulandia.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.perfulandia.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
