# Sistema de Biblioteca – Arquitectura de Microservicios

## Integrantes

- Felipe Pérez
- Felipe Droguett
- Benjamín Venegas

---

# Descripción del Proyecto

Sistema de gestión bibliotecaria desarrollado con Spring Boot y arquitectura de microservicios.

La plataforma permite administrar:

- Usuarios
- Autores
- Categorías
- Libros
- Inventario
- Préstamos
- Reservas
- Multas
- Reportes
- Autenticación

Cada microservicio posee:

- Arquitectura CSR (Controller-Service-Repository)
- Base de datos independiente
- API REST
- Documentación Swagger/OpenAPI
- Pruebas Unitarias con JUnit y Mockito
- Configuración YAML
- Comunicación entre microservicios mediante WebClient y RestTemplate

---

# Arquitectura General

Cliente
↓
API Gateway (8080)
↓
--------------------------------------------------
| Autenticación | Usuarios | Autores | Categorías |
| Libros | Inventario | Préstamos | Reservas |
| Multas | Reportes |
--------------------------------------------------
↓
Bases de Datos MySQL Independientes

---

# Microservicios Implementados

| Microservicio | Puerto |
|--------------|---------|
| api-gateway | 8080 |
| eureka-server | 8761 |
| autenticacion-service | 8089 |
| usuario-service | 8082 |
| autor-service | 8084 |
| categoria-service | 8085 |
| libro-service | 8081 |
| inventario-service | 8090 |
| prestamo-service | 8083 |
| reserva-service | 8087 |
| multa-service | 8086 |
| reporte-service | 8088 |

---

# Rutas Principales del API Gateway

| Ruta Gateway | Servicio |
|-------------|-----------|
| /api/auth/** | autenticacion-service |
| /api/usuarios/** | usuario-service |
| /api/autores/** | autor-service |
| /api/categorias/** | categoria-service |
| /api/libros/** | libro-service |
| /api/inventario/** | inventario-service |
| /api/prestamos/** | prestamo-service |
| /api/reservas/** | reserva-service |
| /api/multas/** | multa-service |
| /api/reportes/** | reporte-service |

---

# Comunicación Entre Microservicios

## WebClient

- libro-service → autor-service
- multa-service → usuario-service

## RestTemplate

- prestamo-service → usuario-service
- prestamo-service → libro-service

---

# Documentación Swagger

## Local

| Servicio | URL |
|-----------|------|
| Usuario | http://localhost:8082/swagger-ui.html |
| Autor | http://localhost:8084/swagger-ui.html |
| Categoría | http://localhost:8085/swagger-ui.html |
| Libro | http://localhost:8081/swagger-ui.html |
| Inventario | http://localhost:8090/swagger-ui.html |
| Préstamo | http://localhost:8083/swagger-ui.html |
| Reserva | http://localhost:8087/swagger-ui.html |
| Multa | http://localhost:8086/swagger-ui.html |
| Reporte | http://localhost:8088/swagger-ui.html |
| Autenticación | http://localhost:8089/swagger-ui.html |

> Reemplazar por URLs públicas si los servicios fueron desplegados en Railway o Render.

---

# Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Cloud Gateway
- Eureka Server
- MySQL
- Maven
- Docker
- Swagger/OpenAPI
- JUnit 5
- Mockito
- GitHub
- Trello

---

# Ejecución Local

## 1. Clonar repositorio

```bash
git clone https://github.com/usuario/repositorio.git
cd biblioteca-microservicios-main
```

## 2. Configurar variables

Crear archivo `.env` usando `.env.example`.

## 3. Levantar Base de Datos

```bash
docker compose up -d mysql
```

## 4. Ejecutar Eureka

```bash
cd eureka-server
mvn spring-boot:run
```

## 5. Ejecutar Microservicios

```bash
mvn spring-boot:run
```

Ejecutar cada servicio en una terminal independiente.

## 6. Ejecutar API Gateway

```bash
cd api-gateway
mvn spring-boot:run
```

---

# Ejecución con Docker

```bash
docker compose up --build
```

Verificar contenedores:

```bash
docker ps
```

---

# Pruebas Unitarias

Ejecutar:

```bash
mvn test
```

Generar cobertura:

```bash
mvn verify
```

Objetivo mínimo de cobertura: **80%**.

---

# Repositorio GitHub

Repositorio público del proyecto:

https://github.com/USUARIO/REPOSITORIO

---

# Gestión del Proyecto

Tablero Trello:

https://trello.com/URL_DEL_TABLERO

---

# Estado del Proyecto

Arquitectura CSR
API Gateway
Eureka Discovery Server

Comunicación REST

Swagger/OpenAPI

Docker

YAML Profiles

Pruebas Unitarias GitHub y Trello
