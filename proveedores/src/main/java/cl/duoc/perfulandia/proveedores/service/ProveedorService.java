package cl.duoc.perfulandia.proveedores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.perfulandia.proveedores.model.Proveedor;
import cl.duoc.perfulandia.proveedores.repository.ProveedorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository provrepository;

    public List<Proveedor> ListarProveedores(){
        return provrepository.findAll();
    }

    public Proveedor Guardar(Proveedor prov){
        return provrepository.save(prov);
    }

    public void Eliminar(Integer idprov){
        provrepository.deleteById(idprov);
    }
    
}
