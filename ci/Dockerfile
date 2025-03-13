# Imagen base
FROM openjdk:21-jdk-slim

# Variables de entorno
ENV APP_HOME=/app

# Copiar el JAR generado por Gradle
COPY build/libs/*.jar $APP_HOME/app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "$APP_HOME/app.jar"]