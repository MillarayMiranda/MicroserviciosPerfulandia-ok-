package cl.duoc.cupon.service;

import cl.duoc.cupon.model.Cupon;
import cl.duoc.cupon.repository.CuponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuponService {

    private final CuponRepository cuponRepository;

    public CuponService(CuponRepository cuponRepository) {
        this.cuponRepository = cuponRepository;
    }

    // Registro de nuevos cupones
    public Cupon crearCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    // Consulta general
    public List<Cupon> obtenerTodosLosCupones() {
        return cuponRepository.findAll();
    }

    // Búsqueda por ID
    public Optional<Cupon> obtenerCuponPorId(Long id) {
        return cuponRepository.findById(id);
    }

    // Validación y canje
    public boolean canjearCupon(String codigo) {
        Optional<Cupon> cuponOpt = cuponRepository.findByCodigo(codigo);
        if (cuponOpt.isPresent() && "ACTIVO".equals(cuponOpt.get().getEstado())) {
            Cupon cupon = cuponOpt.get();
            cupon.setEstado("USADO");
            cuponRepository.save(cupon);
            return true;
        }
        return false;
    }

    // Eliminación
    public void eliminarCupon(Long id) {
        cuponRepository.deleteById(id);
    }
}