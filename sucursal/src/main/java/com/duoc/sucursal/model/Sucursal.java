package com.duoc.sucursal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="SUCURSAL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de Sucursal para Perfulandia")

public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la Sucursal", example = "1")
    @Column(name = "SUCURSAL_ID")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre de la Sucursal", example = "Sucursal Santiago")
    private String nombre;

    @Column(name = "direccion", nullable = false)
    @Schema(description = "Dirección de la Sucursal", example = "Avenida Libertador Bernardo O'Higgins 1234")
    private String direccion;

    @Column(name = "telefono", nullable = false)
    @Schema(description = "Teléfono de contacto de la Sucursal", example = "+56 2 1234 5678")
    private String telefono;

    }
