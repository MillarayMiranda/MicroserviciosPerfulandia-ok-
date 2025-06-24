package cl.duoc.reclamo.repository;

import cl.duoc.reclamo.model.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {

    // Buscar reclamos por ID de usuario
    List<Reclamo> findByUsuarioId(Long usuarioId);

    // Buscar reclamos después de una fecha específica
    List<Reclamo> findByFechaAfter(java.time.LocalDate fecha);
}
