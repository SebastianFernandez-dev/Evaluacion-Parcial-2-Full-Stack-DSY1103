# Evaluación Parcial 2 - Full Stack DSY1103


Proyecto backend desarrollado como parte de la evaluacion parcial de la asignatura **Full Stack** (DSY1103). El sistema esta organizado bajo una arquitectura de **microservicios** para gestionar las distintas areas operativas de un negocio: usuarios, empleados, productos, inventario, pedidos, pagos, envios, proveedores, sucursales y reportes.

---

## Integrantes

- **Zeus Chavez**
- **Edixon Elias**
- **Jhoan Fernandez**

---

## Arquitectura

### Diagrama general

```
                        ┌──────────────┐
                        │   Cliente    │
                        │  (HTTP/REST) │
                        └──────┬───────┘
                               │
                        ┌──────▼───────┐
                        │ API Gateway  │
                        │   :8080      │
                        └──────┬───────┘
                               │
                        ┌──────▼───────┐
                        │   Eureka     │
                        │   Server     │
                        │   :8761      │
                        └──────┬───────┘
          ┌─────────────────────┼─────────────────────┐
          │        ┌────────────┴────────────┐        │
          ▼        ▼        ▼        ▼        ▼        ▼
    ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐
    │Usuarios│ │Product.│ │Invent. │ │Pedidos │ │ Pagos  │ ...
    │:8081   │ │:8082   │ │:8083   │ │:8084   │ │:8085   │
    └────────┘ └────────┘ └────────┘ └────────┘ └────────┘
    ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐
    │Envios  │ │Provee. │ │Sucurs. │ │Emplead.│ │Reportes│
    │:8086   │ │:8087   │ │:8088   │ │:8089   │ │:8090   │
    └────────┘ └────────┘ └────────┘ └────────┘ └────────┘
```

### Comunicacion entre microservicios (Feign)

Cada flecha indica una interfaz Feign que consume endpoints de otro servicio:

```
  ms-usuarios ──────────► ms-pedidos     (obtener pedidos por usuario)
  ms-empleados ─────────► ms-sucursales  (obtener sucursal)
  ms-envios ────────────► ms-usuarios    (obtener usuario)
  ms-envios ────────────► ms-pedidos     (obtener pedido)
  ms-inventario ────────► ms-productos   (obtener producto)
  ms-pagos ─────────────► ms-pedidos     (obtener pedido)
  ms-pedidos ───────────► ms-productos   (obtener producto)
  ms-pedidos ───────────► ms-inventario  (obtener inventario por producto)
  ms-reportes ──────────► ms-usuarios    (obtener usuario)
```

---

## Stack tecnologico

| Tecnologia | Version / Uso |
|---|---|
| **Lenguaje** | Java 21 (JDK 21) |
| **Framework** | Spring Boot 4.0.6 |
| **Spring Cloud** | 2025.1.1 |
| **Build tool** | Maven (wrapped: `mvnw`) |
| **Base de datos** | MySQL 8+ con MySQL Connector/J |
| **ORM** | Spring Data JPA + Hibernate |
| **Documentacion API** | SpringDoc OpenAPI (Swagger UI) v3.0.3 |
| **HATEOAS** | Spring Boot Starter HATEOAS |
| **Service discovery** | Netflix Eureka Server & Client |
| **API Gateway** | Spring Cloud Gateway + LoadBalancer |
| **Comunicacion interna** | Spring Cloud OpenFeign |
| **Migraciones** | Flyway (3 servicios), Liquibase (3 servicios), Hibernate ddl-auto (4 servicios) |
| **Logging** | SLF4J + Logback con archivos rotativos |
| **IDE** | IntelliJ IDEA (`.idea/`) |

---

## Microservicios

| # | Servicio | Puerto | Base de datos | Swagger | Eureka | Migracion |
|---|---|---|---|---|---|---|
| 1 | `ms-usuarios` | `8081` | `prueba1` | Si | Si | Hibernate `ddl-auto: update` |
| 2 | `ms-productos` | `8082` | `prueba1` | Si | No | Hibernate `ddl-auto: update` |
| 3 | `ms-inventario` | `8083` | `prueba2` | Si | Si | Flyway |
| 4 | `ms-pedidos` | `8084` | `prueba3` | Si | Si | Flyway |
| 5 | `ms-pagos` | `8085` | `prueba4` | Si | Si | Flyway |
| 6 | `ms-envios` | `8086` | `prueba5` | No | No | Liquibase |
| 7 | `ms-proveedores` | `8087` | `prueba1` | No | No | Hibernate `ddl-auto: update` |
| 8 | `ms-sucursales` | `8088` | `prueba1` | No | No | Hibernate `ddl-auto: update` |
| 9 | `ms-empleados` | `8089` | `prueba6` | No | No | Liquibase |
| 10 | `ms-reportes` | `8090` | `prueba7` | No | No | Liquibase |

### Infraestructura

| Componente | Puerto | Descripcion |
|---|---|---|
| `eureka-server` | `8761` | Servicio de discovery (Netflix Eureka) |
| `api-gateway` | `8080` | Puerta de enlace unificada (Spring Cloud Gateway) |

### Estructura interna de cada microservicio

```
ms-xxxx/
  src/main/java/com/dsy1103/msxxxx/
    controller/     -- Controladores REST
    service/        -- Logica de negocio
    repository/     -- Acceso a datos (Spring Data JPA)
    modelo/         -- Entidades JPA
    dto/            -- Objetos de transferencia de datos
    mapper/         -- Mapeo Entity <-> DTO
    config/         -- Configuracion (Swagger, etc.)
    exception/      -- Manejador global de excepciones
    client/         -- Interfaces Feign (comunicacion entre servicios)
    runner/         -- Inicializadores de datos (opcional)
  src/main/resources/
    application.yml / application.properties
```

---

## Bases de datos

Todas las conexiones apuntan a `localhost:3306` con usuario `root` y sin contraseña.

| Schema | Microservicios |
|---|---|
| `prueba1` | ms-usuarios, ms-productos, ms-proveedores, ms-sucursales |
| `prueba2` | ms-inventario |
| `prueba3` | ms-pedidos |
| `prueba4` | ms-pagos |
| `prueba5` | ms-envios |
| `prueba6` | ms-empleados |
| `prueba7` | ms-reportes |

---

## API Endpoints

### ms-usuarios (`:8081`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/usuarios` | Listar usuarios |
| `GET` | `/api/v1/usuarios/{id}` | Obtener usuario por ID |
| `POST` | `/api/v1/usuarios` | Crear usuario |
| `PUT` | `/api/v1/usuarios/{id}` | Actualizar usuario |
| `DELETE` | `/api/v1/usuarios/{id}` | Eliminar usuario |
| `GET` | `/api/v1/perfiles` | Listar perfiles |
| `POST` | `/api/v1/perfiles` | Crear perfil |

### ms-productos (`:8082`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/productos` | Listar productos |
| `GET` | `/api/v1/productos/{id}` | Obtener producto por ID |
| `POST` | `/api/v1/productos` | Crear producto |
| `PUT` | `/api/v1/productos/{id}` | Actualizar producto |
| `DELETE` | `/api/v1/productos/{id}` | Eliminar producto |
| `GET` | `/api/v1/categorias` | Listar categorias |

### ms-inventario (`:8083`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/inventario` | Listar inventario |
| `GET` | `/api/v1/inventario/{id}` | Obtener por ID |
| `POST` | `/api/v1/inventario` | Crear registro |
| `PUT` | `/api/v1/inventario/{id}` | Actualizar |
| `DELETE` | `/api/v1/inventario/{id}` | Eliminar |
| `GET` | `/api/v1/movimientostock` | Listar movimientos de stock |

### ms-pedidos (`:8084`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/pedidos` | Listar pedidos |
| `GET` | `/api/v1/pedidos/{id}` | Obtener pedido |
| `POST` | `/api/v1/pedidos` | Crear pedido |
| `PUT` | `/api/v1/pedidos/{id}` | Actualizar pedido |
| `DELETE` | `/api/v1/pedidos/{id}` | Eliminar pedido |
| `GET` | `/api/v1/detalles-pedido` | Listar detalles |

### ms-pagos (`:8085`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/pagos` | Listar pagos |
| `GET` | `/api/v1/pagos/{id}` | Obtener pago |
| `POST` | `/api/v1/pagos` | Crear pago |
| `PUT` | `/api/v1/pagos/{id}` | Actualizar pago |
| `DELETE` | `/api/v1/pagos/{id}` | Eliminar pago |

### ms-envios (`:8086`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/envios` | Listar envios |
| `POST` | `/api/envios` | Crear envio |
| `GET` | `/api/envios/{id}` | Obtener envio |
| `GET` | `/api/envios/no-entregados` | Envios no entregados |

### ms-proveedores (`:8087`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/proveedor` | Listar proveedores |
| `GET` | `/api/v1/proveedor/{id}` | Obtener proveedor |
| `POST` | `/api/v1/proveedor` | Crear proveedor |
| `PUT` | `/api/v1/proveedor/{id}` | Actualizar |
| `DELETE` | `/api/v1/proveedor/{id}` | Eliminar |
| `GET` | `/api/v1/contrato` | Listar contratos |

### ms-sucursales (`:8088`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/sucursal` | Listar sucursales |
| `GET` | `/api/v1/sucursal/{id}` | Obtener sucursal |
| `POST` | `/api/v1/sucursal` | Crear sucursal |
| `PUT` | `/api/v1/sucursal/{id}` | Actualizar |
| `DELETE` | `/api/v1/sucursal/{id}` | Eliminar |
| `GET` | `/api/v1/region` | Listar regiones |

### ms-empleados (`:8089`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/empleados` | Listar empleados |
| `GET` | `/api/v1/empleados/{id}` | Obtener empleado |
| `POST` | `/api/v1/empleados` | Crear empleado |
| `PUT` | `/api/v1/empleados/{id}` | Actualizar |
| `DELETE` | `/api/v1/empleados/{id}` | Eliminar |

### ms-reportes (`:8090`)

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/reporte` | Listar reportes |
| `GET` | `/api/v1/reporte/{id}` | Obtener reporte |
| `POST` | `/api/v1/reporte` | Crear reporte |
| `PUT` | `/api/v1/reporte/{id}` | Actualizar |
| `DELETE` | `/api/v1/reporte/{id}` | Eliminar |

---

## API Gateway

El API Gateway (`:8080`) enruta las peticiones a los microservicios mediante平衡 de carga (LoadBalancer) y Eureka.

### Rutas configuradas

| Ruta de entrada | Servicio destino |
|---|---|
| `/api/v1/usuarios/**`, `/api/v2/usuarios/**` | `ms-usuarios` |
| `/api/v1/perfiles/**`, `/api/v2/perfiles/**` | `ms-usuarios` |
| `/api/v1/inventario/**`, `/api/v2/inventario/**` | `ms-inventario` |
| `/api/v1/movimientostock/**`, `/api/v2/movimientostock/**` | `ms-inventario` |
| `/api/v1/pedidos/**`, `/api/v2/pedidos/**` | `ms-pedidos` |
| `/api/v1/detalles-pedido/**`, `/api/v2/DetallePedidos/**` | `ms-pedidos` |

---

## Documentacion Swagger

Los microservicios con Swagger habilitado exponen su documentacion interactiva en:

| Servicio | URL |
|---|---|
| ms-usuarios | `http://localhost:8081/doc/swagger-ui.html` |
| ms-productos | `http://localhost:8082/doc/swagger-ui.html` |
| ms-inventario | `http://localhost:8083/doc/swagger-ui.html` |
| ms-pedidos | `http://localhost:8084/doc/swagger-ui.html` |
| ms-pagos | `http://localhost:8085/doc/swagger-ui.html` |

---
