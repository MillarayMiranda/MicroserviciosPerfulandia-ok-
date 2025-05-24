package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioServices {

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    @Override
    public List<Inventario> findBySucursalId(Long sucursalId) {
        return inventarioRepository.findBySucursalId(sucursalId);
    }

    @Override
    public Inventario findByProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario update(Long id, Inventario inventario) {
        Optional<Inventario> existingInventario = inventarioRepository.findById(id);
        if (existingInventario.isPresent()) {
            Inventario updatedInventario = existingInventario.get();
            updatedInventario.setCantidad(inventario.getCantidad());
            updatedInventario.setSucursalId(inventario.getSucursalId());
            updatedInventario.setProductoId(inventario.getProductoId());
            return inventarioRepository.save(updatedInventario);
        }
        return null;
    }

}