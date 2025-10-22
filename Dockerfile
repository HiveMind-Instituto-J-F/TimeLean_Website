FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
RUN apt update && apt install -y maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jdk AS runtime
RUN apt update && apt install -y wget unzip \
    && wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.8/bin/apache-tomcat-11.0.8.zip \
    && unzip apache-tomcat-11.0.8.zip -d /usr/local/ \
    && mv /usr/local/apache-tomcat-11.0.8 /usr/local/tomcat
WORKDIR /usr/local/tomcat
COPY --from=builder /app/target/*.war webapps/ROOT.war
EXPOSE 8080
CMD ["bin/catalina.sh", "run"]
