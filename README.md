# SIIERNMA API

Backend del Sistema Integrado de Información Estadística de Recursos Naturales y del Medio Ambiente (SIIERNMA).

Migración de un backend desarrollado originalmente en Spring Boot 3 hacia una arquitectura basada en Spring Framework 5 compatible con Java 8 y Apache Tomcat 9.

## Características

- APIs REST con Spring MVC
- Autenticación y autorización con Spring Security 5
- Persistencia con JPA e Hibernate
- Base de datos PostgreSQL
- Empaquetado WAR para Apache Tomcat 9
- Compatible con Java 8

## Stack Tecnológico

| Tecnología | Versión |
|------------|----------|
| Java | 8 |
| Spring Framework | 5.3.x |
| Spring Security | 5 |
| Spring Data JPA | 2.x |
| Hibernate | 5.x |
| PostgreSQL | 12+ |
| Maven | 3.x |
| Apache Tomcat | 9 |

## Arquitectura

```text
src/main/java/mx/org/inegi/siiernma
├── config
├── controller
├── entity
├── repository
├── service
└── util
```

## Módulos

### Autenticación

```http
POST /api/auth/login
GET  /api/auth/usuario
POST /api/auth/logout
```

### Usuarios

```http
GET /api/prueba/usuarios
```

### Unidades

```http
GET /api/unidad
```

## Configuración

La configuración de base de datos se encuentra en:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
db.url=jdbc:postgresql://HOST:PORT/DATABASE
db.username=USER
db.password=PASSWORD
```

> Nunca subir credenciales reales al repositorio.

## Compilación

```bash
mvn clean install
```

Archivo generado:

```text
target/siscapback.war
```

## Despliegue

Copiar el WAR generado al directorio:

```text
TOMCAT_HOME/webapps/
```

Acceso local:

```text
http://localhost:8080/siscapback
```

## Migración desde Spring Boot 3

Cambios principales:

| Antes | Ahora |
|---------|---------|
| Spring Boot 3 | Spring Framework 5 |
| Spring 6 | Spring 5 |
| Java 17 | Java 8 |
| jakarta.* | javax.* |
| Tomcat embebido | Tomcat 9 |
| JAR | WAR |

## Estado del Proyecto

Actualmente incluye:

- Autenticación
- Usuarios
- Unidades
- PostgreSQL
- JPA/Hibernate
- Spring Security
- Despliegue WAR para Tomcat 9

Los módulos restantes se migrarán gradualmente manteniendo compatibilidad con Java 8 y Spring Framework 5.
