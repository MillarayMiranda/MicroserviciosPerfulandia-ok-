package cl.duoc.reclamo.service;

import cl.duoc.reclamo.model.Reclamo;
import cl.duoc.reclamo.repository.ReclamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReclamoService {

    private final ReclamoRepository reclamoRepository;

    public ReclamoService(ReclamoRepository reclamoRepository) {
        this.reclamoRepository = reclamoRepository;
    }

    // Crear nuevo reclamo
    public Reclamo crearReclamo(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    // Obtener todos los reclamos
    public List<Reclamo> obtenerTodos() {
        return reclamoRepository.findAll();
    }

    // Obtener un reclamo por ID (usando Optional)
    public Optional<Reclamo> obtenerPorId(Long id) {
    return reclamoRepository.findById(id);
    }
    // Eliminar reclamo por ID
    public void eliminarReclamo(Long id) {
        if (!reclamoRepository.existsById(id)) {
            throw new RuntimeException("Reclamo no encontrado con ID: " + id);
        }
        reclamoRepository.deleteById(id);
    }
}
