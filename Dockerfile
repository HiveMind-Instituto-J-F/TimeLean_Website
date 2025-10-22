# =========================
# 1️⃣ Build do projeto com Maven
# =========================
FROM eclipse-temurin:24-jdk AS builder

WORKDIR /app

# Instala Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia apenas o pom.xml primeiro e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Gera o WAR (ignora testes)
RUN mvn clean package -DskipTests

# =========================
# 2️⃣ Tomcat para rodar a aplicação
# =========================
FROM tomcat:11.0-jdk21

# Remove apps padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado pelo Maven para ROOT.war
COPY --from=builder /app/target/HivemindWeb-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD cd /usr/local/tomcat/logs && \
    ls -l && \
    cat catalina.out && \
    tail -n 50 catalina.out && \
    tail -f catalina.out


# Expondo porta 8080
EXPOSE 8080

# Comando padrão do Tomcat
CMD ["catalina.sh", "run"]
