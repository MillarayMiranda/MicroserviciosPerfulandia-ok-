package cl.duoc.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import cl.duoc.producto.assembler.ProductoModelAssembler;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.services.ProductoServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Endpoints de los productos de Perfulandia")
public class ProductoController {

    @Autowired
    private ProductoServices productoServices;
    
    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentran productos registrados",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encuentran productos registrados")))
    })
    public ResponseEntity<?> listarProductos() {
        List<Producto> productos = productoServices.obtenerTodosProductos();
        if(productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran productos registrados");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Producto no encontrado")))    
    })
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        try {
            Producto producto = productoServices.obtenerProductoPorId(id);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Producto creado exitosamente"))),
        @ApiResponse(responseCode = "404", description = "Error al registrar el producto",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al registrar producto"))),
    })
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoServices.crearProducto(producto);
            return ResponseEntity.ok(assembler.toModel(nuevoProducto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar producto");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado para eliminar",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Producto inexistente")))   
    })
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        try {
            Producto producto = productoServices.obtenerProductoPorId(id);
            productoServices.eliminarProducto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto inexistente");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los detalles de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado para actualizar",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Producto no existe")))
    })
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoServices.actualizarProducto(id, producto);
            return ResponseEntity.ok(assembler.toModel(productoActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no existe");
        }
    }
}