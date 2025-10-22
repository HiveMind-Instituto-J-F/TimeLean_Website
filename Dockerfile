# Etapa 1 — Build do WAR com Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos necessários e instala dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código e gera o WAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 — Deploy no Tomcat 11.0.8 com JDK 24
FROM tomcat:11.0.8-jdk24

# Remove o app padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copia o WAR gerado do build anterior
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta padrão do Tomcat
EXPOSE 8080

# Comando para iniciar o Tomcat
CMD ["catalina.sh", "run"]
