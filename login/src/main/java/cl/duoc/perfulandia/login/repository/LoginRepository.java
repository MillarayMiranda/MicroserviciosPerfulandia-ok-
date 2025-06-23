package cl.duoc.perfulandia.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.perfulandia.login.model.Login;

public interface LoginRepository extends JpaRepository<Login, String>{

}
