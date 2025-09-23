FROM eclipse-temurin:21-jdk-alpine
# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR de la aplicación
COPY ../applications/app-service/build/libs/reportsCrediya.jar /app/reportsCrediya.jar
COPY .env /app/.env
# Exponer puerto
EXPOSE 8282

# Punto de entrada
ENTRYPOINT ["java","-jar","reportsCrediya.jar"]
