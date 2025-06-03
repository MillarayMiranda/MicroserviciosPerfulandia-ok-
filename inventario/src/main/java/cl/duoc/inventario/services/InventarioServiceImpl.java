package cl.duoc.inventario.services;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.repository.InventarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Inventario> findBySucursalId(Long sucursalId) {
        return inventarioRepository.findBySucursalId(sucursalId);
    }

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario findByProductoId(Long productoId) {
        return (Inventario) inventarioRepository.findByProductoId(productoId);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario update(Long id, Inventario inventario) {
        if (inventarioRepository.existsById(id)) {
            inventario.setId(id); // Asegura que el ID sea el correcto
            return inventarioRepository.save(inventario);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void crearInventario(Inventario nuevoInventario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearInventario'");
    }
}