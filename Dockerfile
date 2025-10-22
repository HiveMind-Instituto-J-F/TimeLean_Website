FROM tomcat:11.0-jdk21

# Remove o app de exemplo padrão
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copia seu WAR gerado para o Tomcat
COPY target/HivemindWeb.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080
EXPOSE 8080

# Inicia o Tomcat
CMD ["catalina.sh", "run"]
