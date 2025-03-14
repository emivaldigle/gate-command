# Sistema de Control y Monitoreo de Accesos Vehiculares

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/java-21+-green.svg)](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

## Descripción del Proyecto

El **Sistema de Control y Monitoreo de Accesos Vehiculares** es una aplicación diseñada para gestionar de manera eficiente y segura el acceso de usuarios, vehículos y visitantes en entornos controlados. Este sistema permite administrar:

- **Usuarios:** Creación, actualización y eliminación de perfiles de usuarios (residentes, administradores, conserjes).
- **Entidades:** Gestión de entidades asociadas a los usuarios (por ejemplo, comunidades, empresas o residencias).
- **Puntos de Control:** Configuración y monitoreo de puntos de acceso para vehículos y personas.
- **Visitas Autorizadas:** Registro y seguimiento de visitas autorizadas.
- **Eventos:** Registro de actividades y eventos relacionados con el acceso.

Este proyecto utiliza tecnologías modernas como **Spring Boot**, **Hibernate**, **Liquibase**, **Swagger**, y un diseño modular basado en principios de arquitectura limpia y mantenibilidad.

---

## Características Principales

### 1. Gestión de Usuarios
- Creación y administración de perfiles de usuarios con diferentes roles (Administrador, Residente, Conserje).
- Carga masiva de usuarios desde archivos CSV o XLSX.
- Desactivación masiva de usuarios mediante archivos.

### 2. Gestión de Entidades
- Registro y administración de entidades (comunidades, empresas, etc.).
- Asociación de usuarios a entidades específicas.

### 3. Puntos de Control
- Configuración de puntos de control para vehículos y peatones.
- Monitoreo en tiempo real del estado de los puntos de control.

### 4. Visitas Autorizadas
- Registro de visitas con detalles como nombre, placa del vehículo, fecha de expiración, etc.
- Notificaciones automáticas para residentes sobre visitas pendientes.

### 5. Gestión de estacionamientos
- Registro y actualización en tiempo real de estacionamientos

### 6. Eventos y Logs
- Registro de eventos relacionados con accesos vehiculares y peatonales.
- Generación de informes detallados para auditoría.

### 7. Seguridad
- Autenticación y autorización robusta mediante **Spring Security**.
- Encriptación de contraseñas y datos sensibles.

---

## Tecnologías Utilizadas

### Backend
- **Spring Boot**: Framework principal para la creación de APIs RESTful.
- **Spring Data JPA**: Manejo de persistencia y consultas a la base de datos.
- **Hibernate**: Mapeo objeto-relacional (ORM).
- **Liquibase**: Gestión de migraciones de base de datos.
- **Spring Security**: Implementación de autenticación y autorización.
- **Swagger/OpenAPI**: Documentación interactiva de las APIs.

### Base de Datos
- **H2 (Testing)**: Base de datos en memoria para pruebas unitarias e integración.
- **PostgreSQL (Producción)**: Base de datos relacional robusta.

### Otros
- **Maven/Gradle**: Gestión de dependencias y construcción del proyecto.
- **JUnit 5/Mockito**: Pruebas unitarias e integración.
- **Logback**: Sistema de logging para monitorear el comportamiento del sistema.

---

## Instalación y Configuración

### Requisitos Previos
1. **Java 21+**: Asegúrate de tener instalada la versión 21 o superior de Java.
2. **Maven/Gradle**: Instala Maven o Gradle para construir el proyecto.
3. **PostgreSQL**: Configura una instancia de PostgreSQL para producción.

### Pasos para Ejecutar el Proyecto

1. **Clonar el Repositorio**
   ```bash
   git clone git@github.com:emivaldigle/gate-command.git
2. **Configurar Propiedades**
Edita el archivo `application.yml` o `application.properties` para configurar la conexión a la base de datos:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/nombre_base_datos
       username: tu_usuario
       password: tu_contraseña
     jpa:
       hibernate:
         ddl-auto: update

2. **Construir el proyecto**
```bash
   gradle clean build