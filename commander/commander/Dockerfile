# build
FROM maven:3.9.12-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
#Compila o projeto criando o arqquivo .jar
RUN mvn clean package -DskipTests
#Execucao da imagem
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
#Copia o arquivo .jar gerado 
COPY --from=build /app/target/*.jar app.jar
# Porta
EXPOSE 8080
#Comando para rodar
ENTRYPOINT ["java", "-jar", "app.jar"]