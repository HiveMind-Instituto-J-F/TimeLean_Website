FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
RUN apt update && apt install -y maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:11.0-jdk21
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080