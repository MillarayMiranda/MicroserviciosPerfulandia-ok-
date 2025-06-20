# 🧴 Perfulandia SPA - Backend Microservices

**Proyecto académico** desarrollado en Java con Spring Boot y Maven utilizando bases de datos NoSQL (MongoDB) y arquitectura de microservicios.  
Este sistema gestiona una perfumería con múltiples perfiles de usuario (administradores, gerentes, empleados y clientes).

---

## 📐 Arquitectura General

| Microservicio       | Funcionalidad principal                          |
|---------------------|--------------------------------------------------|
| `usuario-service`   | Gestión de usuarios y autenticación              |
| `producto-service`  | Gestión del catálogo de productos                |
| `inventario-service`| Control del stock por sucursal                   |
| `pedido-service`    | Procesamiento y seguimiento de pedidos           |
| `factura-service`   | Emisión de facturas electrónicas (opcional)      |
| `envio-service`     | Gestión de envíos y estado logístico (opcional)  |

Comunicación entre microservicios se realiza vía **REST**.  
Cada microservicio es autónomo y posee su propia base de datos MongoDB.

---

## ⚙️ Tecnologías

- Java 17+
- Spring Boot
- Spring Web
- Spring Data MongoDB
- Maven
- MongoDB
- Docker (opcional)
- Postman (para pruebas)

---

## 📁 Estructura Sugerida por Microservicio

```
/nombre-microservicio
├── src
│   └── main/java/com/perfulandia/{servicio}
│       ├── controller/
│       ├── model/
│       ├── repository/
│       ├── service/
│       └── Application.java
├── resources/
│   ├── application.yml
│   └── data/roles.json (usuario-service)
```

---

## 🧠 Detalle por Microservicio

### 1. 🧑‍💼 `usuario-service` hecho

**Responsabilidad:** Registrar, autenticar y administrar usuarios del sistema (clientes, empleados, etc.).

#### Modelo: `Usuario`
- id: String
- nombre: String
- correo: String
- contrasena: String
- rolId: String
- direccion: String
- telefono: String
- activo: Boolean

#### Endpoints
- POST /usuarios
- GET /usuarios
- GET /usuarios/{id}
- PUT /usuarios/{id}
- DELETE /usuarios/{id}
- POST /auth/login

### 2. 🛍 `producto-service` hecho

**Responsabilidad:** Gestionar productos disponibles para venta.

#### Modelo: `Producto`
- id: String
- nombre: String
- descripcion: String
- precio: Double
- stock: Integer
- categoria: String
- activo: Boolean

#### Endpoints
- POST /productos
- GET /productos
- GET /productos/{id}
- PUT /productos/{id}
- DELETE /productos/{id}

### 3. 📦 `inventario-service` hecho

**Responsabilidad:** Controlar el inventario en distintas sucursales.

#### Modelo: `Inventario`
- id: String
- productoId: String
- sucursalId: String
- cantidad: Integer

#### Endpoints
- GET /inventario?sucursalId={id}
- GET /inventario/producto/{id}
- POST /inventario
- PUT /inventario/{id}
- DELETE /inventario/{id}

### 4. 📑 `pedido-service` en proceso

**Responsabilidad:** Gestionar el ciclo de vida de un pedido.

#### Modelo: `Pedido`
- id: String
- usuarioId: String
- fecha: Date
- estado: String
- productos: List<Object>
- total: Double

#### Endpoints
- POST /pedidos
- GET /pedidos/usuario/{id}
- GET /pedidos/{id}
- PUT /pedidos/{id}

### 5. 🧾 `factura-service` (opcional)

**Responsabilidad:** Emitir y consultar facturas electrónicas.

#### Modelo: `Factura`
- id: String
- pedidoId: String
- usuarioId: String
- fecha: Date
- total: Double
- metodoPago: String

#### Endpoints
- POST /facturas
- GET /facturas/{id}
- GET /facturas/usuario/{id}

### 6. 🚚 `envio-service` (opcional)

**Responsabilidad:** Controlar los envíos de productos a clientes o entre sucursales.

#### Modelo: `Envio`
- id: String
- pedidoId: String
- direccionEntrega: String
- estadoEnvio: String
- fechaEnvio: Date

#### Endpoints
- POST /envios
- PUT /envios/{id}/estado
- GET /envios/pedido/{id}

---

## ✅ Consideraciones Finales

- No es necesario un microservicio para roles. La colección `roles` vive dentro de `usuario-service` al igual que la colección `sucursales` vive dentro de inventario-service.
- Cada microservicio tendrá su propia base de datos MongoDB.
- El diseño está orientado a cumplir con los requerimientos del proyecto académico sin sobrecomplejidad.
- Se recomienda implementar autenticación JWT para proteger rutas sensibles.
- La documentación de cada servicio puede enriquecerse con Swagger/OpenAPI.



## Hacer microservicio de reporte y login