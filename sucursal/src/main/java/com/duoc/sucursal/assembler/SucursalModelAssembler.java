package com.duoc.sucursal.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.duoc.sucursal.model.Sucursal;

@Component
public class SucursalModelAssembler implements  RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(
            sucursal,
            linkTo(methodOn(SucursalController.class).obtenerTodasSucursales()).withRel("Lista las sucursales"),
            linkTo(methodOn(SucursalController.class).obtenerSucursalPorId(sucursal.getId())).withRel("Obtiene una sucursal por ID"),
            linkTo(methodOn(SucursalController.class).crearSucursal(sucursal)).withRel("Crea una nueva sucursal"),
            linkTo(methodOn(SucursalController.class).actualizarSucursal(sucursal.getId(), sucursal)).withRel("Actualiza una sucursal"),
            linkTo(methodOn(SucursalController.class).eliminarSucursal(sucursal.getId())).withRel("Elimina una sucursal por ID")
        );
    }
    
}
