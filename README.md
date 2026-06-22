# Evaluación Parcial 2 - Full Stack DSY1103

Proyecto desarrollado como parte de la evaluación parcial de la asignatura **Full Stack**.  
El sistema está organizado bajo una arquitectura de **microservicios** para gestionar distintas áreas del negocio.

---

## Integrantes

- **Zeus Chavez**
- **Edixon Elias**
- **Jhoan Fernandez**

---

## Descripción

Este repositorio contiene una solución **backend** dividida en varios microservicios,
orientados a la administración de diferentes módulos del sistema, como:

- Usuarios
- Empleados
- Pedidos
- Productos
- Inventario
- Pagos
- Envíos
- Proveedores
- Sucursales
- Reportes

---

## Arquitectura y tecnologías

| Tecnología       | Versión / Uso                           |
|----------------|------------------------------------------|
| Lenguaje       | Java 21                                  |
| Framework      | Spring Boot                              |
| Gestión de dependencias | Maven                            |
| Comunicación entre servicios | Spring Cloud OpenFeign         |
| Estructura     | Microservicios (capa: controller, service, repository, modelo, dto, mapper, config, exception) |

---

## Microservicios incluidos

1. `ms-usuarios`
2. `ms-empleados`
3. `ms-envios`
4. `ms-inventario`
5. `ms-pagos`
6. `ms-pedidos`
7. `ms-productos`
8. `ms-proveedores`
9. `ms-reportes`
10. `ms-sucursales`

Cada microservicio sigue una **organización por capas**, incluyendo carpetas como:
controller/
service/
repository/
modelo/
dto/
mapper/
config/
exception/


---

## Requisitos previos

- **Java 21** (JDK 21)
- **Maven** (3.8+)

---

## Comunicación entre servicios
Se utiliza Spring Cloud OpenFeign para la comunicación declarativa entre microservicios.
Cada servicio puede consumir endpoints de otro mediante interfaces Feign client.

