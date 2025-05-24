package com.duoc.sucursal.services;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.repository.SucursalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalServiceImpl implements SucursalServices {

    private final SucursalRepository sucursalRepository;

    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public Sucursal crearSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public List<Sucursal> obtenerTodasSucursales() {
        return sucursalRepository.findAll();
    }

    @Override
    public Sucursal obtenerSucursalPorId(Long id) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(id);
        return sucursal.orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));
    }

    @Override
    public Sucursal actualizarSucursal(Long id, Sucursal sucursalActualizada) {
        return sucursalRepository.findById(id)
                .map(sucursal -> {
                    sucursal.setNombre(sucursalActualizada.getNombre());
                    sucursal.setDireccion(sucursalActualizada.getDireccion());
                    sucursal.setTelefono(sucursalActualizada.getTelefono());
                    return sucursalRepository.save(sucursal);
                })
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));
    }

    @Override
    public void eliminarSucursal(Long id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sucursal no encontrada con id: " + id);
        }
    }
}