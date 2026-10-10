# Etapa 1: Compilación
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar configuración de Maven Wrapper y dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN ./mvnw package -DskipTests

# Etapa 2: Imagen final de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]