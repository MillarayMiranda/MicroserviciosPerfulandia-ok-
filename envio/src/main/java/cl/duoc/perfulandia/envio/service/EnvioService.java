package cl.duoc.perfulandia.envio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.perfulandia.envio.model.Envio;
import cl.duoc.perfulandia.envio.repository.EnvioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnvioService {

    private final EnvioRepository enviorepository;

    public EnvioService(EnvioRepository enviorepository) {
        this.enviorepository = enviorepository;
    }

    public Envio Guardar(Envio envio){
        return enviorepository.save(envio);
    }

    public Envio ConsultarEnvio(Long id){
        return enviorepository.findById(id).get();
    }

    public List<Envio> ConsultarEnvioTodos(){
        return enviorepository.findAll();
    }

}
