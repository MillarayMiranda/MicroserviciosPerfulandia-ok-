package cl.duoc.producto.model;

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
@Table(name="PRODUCTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de Producto para Perfulandia")

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del Producto", example = "1")
    @Column(name = "PRODUCTO_ID")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del Producto", example = "Chanel N°5")
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción del Producto", example = "Perfume icónico de Chanel con notas florales y amaderadas")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio del Producto", example = "$120.000")
    private Double precio;
    
    @Column(name = "stock", nullable = false)
    @Schema(description = "Cantidad disponible en stock", example = "50")
    private Integer stock;

    @Column(name = "categoria", nullable = false)
    @Schema(description = "Categoría del Producto", example = "Perfumes de lujo")
    private String categoria;

    }