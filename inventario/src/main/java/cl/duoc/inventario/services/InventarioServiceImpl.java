package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.repository.InventarioRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
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
        return inventarioRepository.findByProductoId(productoId).orElse(null);
    }

    @Override
    @Transactional
    public Inventario save(Inventario inventario) {
        // Validaciones adicionales
        if (inventario.getProductoId() == null) {
            throw new IllegalArgumentException("El ID del producto es requerido");
        }
        if (inventario.getSucursalId() == null) {
            throw new IllegalArgumentException("El ID de la sucursal es requerido");
        }
        if (inventario.getCantidad() == null || inventario.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad debe ser un número positivo");
        }
        
        return inventarioRepository.save(inventario);
    }

    @Override
    @Transactional
    public Inventario update(Long id, Inventario inventario) {
        return inventarioRepository.findById(id)
                .map(existingInventario -> {
                    if (inventario.getProductoId() != null) {
                        existingInventario.setProductoId(inventario.getProductoId());
                    }
                    if (inventario.getSucursalId() != null) {
                        existingInventario.setSucursalId(inventario.getSucursalId());
                    }
                    if (inventario.getCantidad() != null) {
                        existingInventario.setCantidad(inventario.getCantidad());
                    }
                    return inventarioRepository.save(existingInventario);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Inventario crearInventario(Inventario inventario) {
        // Validaciones básicas
        if (inventario.getProductoId() == null) {
            throw new IllegalArgumentException("El ID del producto es requerido");
        }
        if (inventario.getSucursalId() == null) {
            throw new IllegalArgumentException("El ID de la sucursal es requerido");
        }
        if (inventario.getCantidad() == null || inventario.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad debe ser un número positivo");
        }
        
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario obtenerInventarioPorId(long l) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerInventarioPorId'");
    }
    
}