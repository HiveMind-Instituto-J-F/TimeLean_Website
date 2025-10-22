# Build do projeto com Maven
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
RUN apt update && apt install -y maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Tomcat para rodar a aplicação
FROM tomcat:11.0-jdk21
# Remove apps padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
# Copia o WAR gerado pelo Maven como ROOT.war
COPY --from=builder /app/target/HivemindWeb-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
