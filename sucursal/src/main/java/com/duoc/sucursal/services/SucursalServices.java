package com.duoc.sucursal.services;

import com.duoc.sucursal.model.Sucursal;
import java.util.List;

public interface SucursalServices {
    Sucursal crearSucursal(Sucursal sucursal);
    List<Sucursal> obtenerTodasSucursales();
    Sucursal obtenerSucursalPorId(Long id);
    Sucursal actualizarSucursal(Long id, Sucursal sucursal);
    void eliminarSucursal(Long id);
}