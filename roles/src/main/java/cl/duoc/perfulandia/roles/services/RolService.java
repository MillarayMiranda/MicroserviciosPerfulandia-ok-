package cl.duoc.perfulandia.roles.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.perfulandia.roles.model.Rol;
import cl.duoc.perfulandia.roles.repository.RolRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolrepository;

    public List<Rol> ListarRoles(){
        return rolrepository.findAll();
    }

    public Rol ConsultarUnRol(Integer idrol){
        return rolrepository.findById(idrol).get();
    }

    public Rol Guardar(Rol rol){
        return rolrepository.save(rol);
    }

    public void Eliminar(Integer idrol){
        rolrepository.deleteById(idrol);
    }

}
