# Sistema de Biblioteca — Arquitectura de Microservicios
Proyecto Semestral — Asignatura DSY1103 Desarrollo FullStack 1  
## Integrantes del Equipo
| Nombre           |
|------------------|
| Felipe Droguett  |
| Benjamin Venega  |
| Felipe Perez     |
##  Descripción del Proyecto
Sistema de gestión de biblioteca desarrollado bajo una arquitectura distribuida de microservicios independientes con Spring Boot. El sistema permite administrar libros, autores, usuarios, préstamos, reservas, multas, inventario, categorías, reportes y autenticación, cada uno como un servicio autónomo con su propia base de datos MySQL.
Cada microservicio implementa el patrón **CSR (Controller → Service → Repository)**, persistencia real con **JPA + Hibernate**, validaciones con **Bean Validation (JSR 380)**, manejo centralizado de errores con `@RestControllerAdvice`, logs estructurados con **SLF4J**, y comunicación entre servicios mediante **WebClient** y **RestTemplate**.
##  Arquitectura General
```
┌─────────────────────────────────────────────────────────────┐
│                    MICROSERVICIOS                            │
│                                                             │
│  [autenticacion]  [usuario]  [autor]   [categoria]         │
│       :8089          :8082     :8084       :8085            │
│                                                             │
│  [libro]   [inventario]  [prestamo]  [reserva]             │
│   :8081       :8090         :8083      :8087                │
│                                                             │
│  [multa]        [reporte]                                   │
│   :8086           :8088                                     │
│                                                             │
│  Comunicación: WebClient (libro→autor, multa→usuario)       │
│                RestTemplate (prestamo→libro, prestamo→usuario)│
└─────────────────────────────────────────────────────────────┘
         │ cada servicio │
         ▼               ▼
   [MySQL :3306]   [MySQL :3306]  ...  (BD separada por servicio)
```
## Microservicios
| Servicio            | Puerto | Base de datos        | Descripción                              |
|---------------------|--------|----------------------|------------------------------------------|
| autenticacion-service | 8089 | `autenticacion_db`   | Gestión de credenciales de acceso        |
| usuario-service      | 8082  | `usuario_db`         | Gestión de usuarios de la biblioteca     |
| autor-service        | 8084  | `autor_db`           | Gestión de autores de libros             |
| categoria-service    | 8085  | `categoria_db`       | Categorías y clasificación de libros     |
| libro-service        | 8081  | `libro_db`           | Catálogo de libros disponibles           |
| inventario-service   | 8090  | `inventario_db`      | Control de stock por libro               |
| prestamo-service     | 8083  | `prestamo_db`        | Registro y gestión de préstamos          |
| reserva-service      | 8087  | `reserva_db`         | Reservas de libros por usuarios          |
| multa-service        | 8086  | `multa_db`           | Gestión de multas por mora               |
| reporte-service      | 8088  | `reporte_db`         | Generación de reportes del sistema       |
---
## Comunicación entre Microservicios
| Origen            | Destino          | Tecnología   | Propósito                                 |
|-------------------|------------------|--------------|-------------------------------------------|
| libro-service     | autor-service    | WebClient    | Validar que el autor existe al crear libro |
| multa-service     | usuario-service  | WebClient    | Validar usuario al registrar multa        |
| prestamo-service  | libro-service    | RestTemplate | Validar existencia del libro              |
| prestamo-service  | usuario-service  | RestTemplate | Validar existencia del usuario            |
---
##  Endpoints REST por Microservicio
### autenticacion-service (`:8089/autenticacion`)
| Método | Ruta           | Descripción                    |
|--------|----------------|--------------------------------|
| GET    | `/autenticacion`     | Listar todas las credenciales  |
| GET    | `/autenticacion/{id}` | Buscar credencial por ID      |
| POST   | `/autenticacion`     | Crear nueva credencial         |
| DELETE | `/autenticacion/{id}` | Eliminar credencial           |
### usuario-service (`:8082/usuarios`)
| Método | Ruta            | Descripción              |
|--------|-----------------|--------------------------|
| GET    | `/usuarios`     | Listar todos los usuarios |
| GET    | `/usuarios/{id}` | Buscar usuario por ID   |
| POST   | `/usuarios`     | Crear usuario            |
| PUT    | `/usuarios/{id}` | Actualizar usuario      |
| DELETE | `/usuarios/{id}` | Eliminar usuario        |
### autor-service (`:8084/autores`)
| Método | Ruta          | Descripción            |
|--------|---------------|------------------------|
| GET    | `/autores`    | Listar todos los autores |
| GET    | `/autores/{id}` | Buscar autor por ID  |
| POST   | `/autores`    | Crear autor            |
| PUT    | `/autores/{id}` | Actualizar autor     |
| DELETE | `/autores/{id}` | Eliminar autor       |
### categoria-service (`:8085/categorias`)
| Método | Ruta              | Descripción              |
|--------|-------------------|--------------------------|
| GET    | `/categorias`     | Listar categorías        |
| GET    | `/categorias/{id}` | Buscar categoría por ID |
| POST   | `/categorias`     | Crear categoría          |
| DELETE | `/categorias/{id}` | Eliminar categoría      |
### libro-service (`:8081/libros`)
| Método | Ruta           | Descripción             |
|--------|----------------|-------------------------|
| GET    | `/libros`      | Listar todos los libros  |
| GET    | `/libros/{id}` | Buscar libro por ID     |
| POST   | `/libros`      | Crear libro             |
| PUT    | `/libros/{id}` | Actualizar libro        |
| DELETE | `/libros/{id}` | Eliminar libro          |
### inventario-service (`:8090/inventario`)
| Método | Ruta               | Descripción                   |
|--------|--------------------|-------------------------------|
| GET    | `/inventario`      | Listar registros de inventario |
| GET    | `/inventario/{id}` | Buscar registro por ID        |
| POST   | `/inventario`      | Registrar stock               |
| DELETE | `/inventario/{id}` | Eliminar registro             |
### prestamo-service (`:8083/prestamos`)
| Método | Ruta                    | Descripción                        |
|--------|-------------------------|------------------------------------|
| GET    | `/prestamos`            | Listar préstamos                   |
| GET    | `/prestamos/{id}`       | Buscar préstamo por ID             |
| POST   | `/prestamos`            | Crear préstamo (valida usuario y libro) |
| PUT    | `/prestamos/{id}/estado` | Actualizar estado (ACTIVO/DEVUELTO) |
| DELETE | `/prestamos/{id}`       | Eliminar préstamo                  |
### reserva-service (`:8087/reservas`)
| Método | Ruta             | Descripción             |
|--------|------------------|-------------------------|
| GET    | `/reservas`      | Listar reservas         |
| GET    | `/reservas/{id}` | Buscar reserva por ID   |
| POST   | `/reservas`      | Crear reserva           |
| PUT    | `/reservas/{id}` | Actualizar reserva      |
| DELETE | `/reservas/{id}` | Eliminar reserva        |
### multa-service (`:8086/multas`)
| Método | Ruta            | Descripción                        |
|--------|-----------------|------------------------------------|
| GET    | `/multas`       | Listar multas                      |
| GET    | `/multas/{id}`  | Buscar multa por ID                |
| POST   | `/multas`       | Crear multa (valida usuario remoto) |
| PUT    | `/multas/{id}`  | Actualizar multa                   |
| DELETE | `/multas/{id}`  | Eliminar multa                     |
### reporte-service (`:8088/reportes`)
| Método | Ruta              | Descripción             |
|--------|-------------------|-------------------------|
| GET    | `/reportes`       | Listar reportes         |
| GET    | `/reportes/{id}`  | Buscar reporte por ID   |
| POST   | `/reportes`       | Crear reporte           |
| PUT    | `/reportes/{id}`  | Actualizar reporte      |
| DELETE | `/reportes/{id}`  | Eliminar reporte        |
---
##  Requisitos Previos
- **Java 17** o superior
- **Maven 3.8+**
- **MySQL 8.0+** corriendo en `localhost:3306`
- **Postman** u otra herramienta REST para pruebas (opcional)
---
##Configuración de Bases de Datos
Antes de ejecutar cualquier microservicio, crea las 10 bases de datos en MySQL:
```sql
CREATE DATABASE IF NOT EXISTS autenticacion_db;
CREATE DATABASE IF NOT EXISTS usuario_db;
CREATE DATABASE IF NOT EXISTS autor_db;
CREATE DATABASE IF NOT EXISTS categoria_db;
CREATE DATABASE IF NOT EXISTS libro_db;
CREATE DATABASE IF NOT EXISTS inventario_db;
CREATE DATABASE IF NOT EXISTS prestamo_db;
CREATE DATABASE IF NOT EXISTS reserva_db;
CREATE DATABASE IF NOT EXISTS multa_db;
CREATE DATABASE IF NOT EXISTS reporte_db;
```
> Las tablas se crean automáticamente al iniciar cada servicio gracias a `spring.jpa.hibernate.ddl-auto=update`.
---
## ▶Pasos para Ejecutar
### Opción A — Ejecutar cada servicio individualmente
Abre una terminal por cada microservicio y ejecuta dentro de su carpeta:
# 1. usuario-service (iniciar primero, otros dependen de él)
cd usuario-service/usuario-service
./mvnw spring-boot:run
# 2. autor-service
cd autor-service/autor-service
./mvnw spring-boot:run
# 3. categoria-service
cd categoria-service/categoria-service
./mvnw spring-boot:run
# 4. libro-service
cd libro-service/libro-service
./mvnw spring-boot:run
# 5. inventario-service
cd inventario-service/inventario-service
./mvnw spring-boot:run
# 6. autenticacion-service
cd autenticacion-service/autenticacion-service
./mvnw spring-boot:run
# 7. prestamo-service (depende de usuario y libro)
cd prestamo-service/prestamo-service
./mvnw spring-boot:run
# 8. reserva-service
cd reserva-service/reserva-service
./mvnw spring-boot:run
# 9. multa-service (depende de usuario)
cd multa-service/multa-service
./mvnw spring-boot:run
# 10. reporte-service
cd reporte-service/reporte-service
./mvnw spring-boot:run
### Opción B — Ejecutar desde IntelliJ IDEA o VSCode
1. Abre cada carpeta de microservicio como proyecto Maven independiente.
2. Ejecuta la clase principal `*Application.java` de cada servicio.
3. Respeta el orden de inicio indicado en la Opción A.
## Credenciales MySQL por Defecto
Cada `application.properties` usa las siguientes credenciales (ajusta si tu configuración es diferente):
properties
spring.datasource.username=root
spring.datasource.password=           # vacío por defecto
Si tu MySQL tiene contraseña, edita el campo `spring.datasource.password` en el `application.properties` de **cada** microservicio.
## Ejemplo de Prueba con Postman
### Crear un usuario
POST http://localhost:8082/usuarios
Content-Type: application/json
{
  "nombre": "Ana González",
  "correo": "ana@biblioteca.cl",
  "telefono": "912345678"
}
### Crear un autor
POST http://localhost:8084/autores
Content-Type: application/json
{
  "nombre": "Gabriel García Márquez",
  "nacionalidad": "Colombiana"
}
### Crear un libro
POST http://localhost:8081/libros
Content-Type: application/json
{
  "titulo": "Cien Años de Soledad",
  "autor": "Gabriel García Márquez",
  "disponible": true
}
### Crear un préstamo (valida usuario y libro automáticamente)
POST http://localhost:8083/prestamos
Content-Type: application/json
{
  "usuarioId": 1,
  "libroId": 1,
  "fechaPrestamo": "2025-05-16"
}
### Actualizar estado de un préstamo a DEVUELTO
PUT http://localhost:8083/prestamos/1/estado?estado=DEVUELTO
## Tecnologías Utilizadas
| Tecnología          | Uso                                         |
|---------------------|---------------------------------------------|
| Spring Boot 3.x     | Framework base de cada microservicio        |
| Spring Data JPA     | Acceso a datos y operaciones CRUD           |
| Hibernate           | ORM y mapeo de entidades                    |
| MySQL 8             | Motor de base de datos relacional           |
| Bean Validation (JSR 380) | Validación de datos en DTOs          |
| WebClient           | Comunicación reactiva entre microservicios  |
| RestTemplate        | Comunicación HTTP entre microservicios      |
| SLF4J + Lombok @Slf4j | Logs estructurados por capas            |
| Lombok              | Reducción de boilerplate (getters, setters) |
| Maven               | Gestión de dependencias y build             |
##  Estructura de Cada Microservicio
{nombre}-service/
└── src/main/java/com/biblioteca/{nombre}_service/
    ├── controller/      ← Endpoints REST, recibe y responde solicitudes HTTP
    ├── service/         ← Lógica de negocio y reglas del dominio
    ├── repository/      ← Acceso a datos via JpaRepository
    ├── model/           ← Entidades JPA (@Entity)
    ├── dto/             ← Objetos de transferencia (Request/Response)
    ├── exception/       ← GlobalExceptionHandler (@RestControllerAdvice)
    ├── client/          ← Clientes HTTP para otros microservicios
    ├── config/          ← Configuración de beans (WebClient, RestTemplate)
    └── mapper/          ← Mapeo entre entidades y DTOs
##  Notas Importantes
- Los servicios `prestamo-service` y `multa-service` **requieren** que `usuario-service` esté activo para funcionar correctamente, ya que validan la existencia del usuario antes de registrar.
- `prestamo-service` también requiere que `libro-service` esté activo para validar el libro.
- `libro-service` se comunica con `autor-service` para obtener información del autor.
- Si un servicio externo no está disponible, se retorna un error HTTP 503 con mensaje descriptivo.
