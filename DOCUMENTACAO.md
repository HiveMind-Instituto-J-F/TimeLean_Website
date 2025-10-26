## Documentação do Projeto TimeLean

Este documento detalha a arquitetura, estrutura e componentes do projeto TimeLean, um aplicativo web desenvolvido em Java. O objetivo é fornecer uma visão abrangente do projeto, facilitando a manutenção e o desenvolvimento de novas funcionalidades.

### Requisitos

- **Tomcat 11+**: Servidor de aplicação para executar os Servlets e JSPs.
- **JDK 24**: Java Development Kit, versão 24 ou superior.

### Tecnologias Utilizadas

O projeto é construído sobre um conjunto de tecnologias padrão da indústria para desenvolvimento web com Java:

- **Java Servlets**: Para processar requisições e respostas HTTP, atuando como os controladores da aplicação.
- **JavaServer Pages (JSP)**: Para criar páginas web dinâmicas, atuando como as views.
- **JDBC (Java Database Connectivity)**: Para conectar e interagir com o banco de dados PostgreSQL.
- **Maven**: Para gerenciamento de dependências e automação do build.
- **Dotenv**: Para gerenciamento de variáveis de ambiente, mantendo as configurações sensíveis fora do código-fonte.
- **HTML, CSS, JavaScript**: Para a estrutura, estilo e interatividade do front-end.

### Estrutura do Projeto

O projeto segue uma estrutura de diretórios padrão para aplicações web Java, promovendo a separação de responsabilidades entre as diferentes camadas da aplicação.

- `src/main/java/hivemind/hivemindweb/`: Contém todo o código-fonte Java da aplicação.
- `Connection/`: Responsável pela conexão com o banco de dados.
- `DAO/`: Contém os Data Access Objects (DAOs), que abstraem o acesso aos dados.
- `Exception/`: Classes de exceção customizadas.
- `Servelts/`: Controladores que gerenciam o fluxo da aplicação.
- `Services/`: Serviços auxiliares e listeners.
- `config/`: Classes de configuração, como o carregador de variáveis de ambiente.
- `models/`: Classes de modelo (POJOs) que representam as entidades do sistema.
- `src/main/resources/META-INF/`: Arquivos de configuração para o servidor de aplicação (Tomcat), como `context.xml`.
- `src/main/webapp/`: Contém todos os recursos web.
- `css/`, `js/`, `img/`: Contêm os arquivos estáticos do front-end.
- `html/`: Contém as páginas HTML e JSP.
- `WEB-INF/`: Contém o descritor de implantação (`web.xml`).

### Componentes Chave

#### `EnvLoader.java`

Esta classe é responsável por carregar as variáveis de ambiente de um arquivo `.env` localizado no diretório `/WEB-INF/`. Ela utiliza a biblioteca `java-dotenv` para ler o arquivo e disponibilizar as variáveis para a aplicação. Isso é crucial para a segurança, pois permite que informações sensíveis, como credenciais de banco de dados, sejam mantidas fora do controle de versão.

#### `DBConnection.java`

Fornece os métodos para conectar e desconectar do banco de dados PostgreSQL. Ele utiliza as variáveis de ambiente carregadas pelo `EnvLoader` para obter as credenciais de conexão. O método `connected()` retorna um objeto `Connection` que é utilizado pelos DAOs para executar as queries SQL.

#### `AppListener.java`

Implementa a interface `ServletContextListener`, o que permite executar código na inicialização e finalização da aplicação. No método `contextInitialized()`, ele inicializa o `EnvLoader` para garantir que as variáveis de ambiente estejam disponíveis desde o início. O método `contextDestroyed()` é utilizado para liberar recursos quando a aplicação é encerrada.

#### Data Access Objects (DAOs)

Os DAOs (como `AdminDAO`, `CompanyDAO`, etc.) são um padrão de projeto que isola a lógica de acesso a dados do resto da aplicação. Cada DAO é responsável por realizar as operações de CRUD (Create, Read, Update, Delete) para uma entidade específica do modelo. Eles utilizam o `DBConnection` para obter uma conexão com o banco de dados e `PreparedStatement` para executar as queries de forma segura, prevenindo ataques de injeção de SQL.

#### Servlets

Os Servlets (como `HomeServelet`, `LoginServlet`, etc.) atuam como os controladores no padrão MVC. Eles são mapeados para URLs específicas (através da anotação `@WebServlet` ou do `web.xml`) e processam as requisições HTTP. Eles recebem os dados da requisição, interagem com os DAOs para acessar o banco de dados e, por fim, encaminham a requisição para uma página JSP para renderizar a resposta.

#### Modelos (POJOs)

As classes no pacote `models` (como `Admin.java`, `Company.java`, etc.) são Plain Old Java Objects (POJOs). Elas representam as entidades do sistema e encapsulam os dados com seus respectivos getters e setters. Essas classes são utilizadas para transportar dados entre as camadas da aplicação (dos DAOs para os Servlets e dos Servlets para os JSPs).

### Fluxo de uma Requisição

1.  O usuário acessa uma URL no navegador.
2.  O Tomcat recebe a requisição e, com base na URL, a encaminha para o Servlet correspondente.
3.  O Servlet processa a requisição, podendo ler parâmetros, interagir com os DAOs para buscar ou salvar dados no banco de dados.
4.  O Servlet adiciona os dados necessários como atributos da requisição.
5.  O Servlet encaminha a requisição para um arquivo JSP.
6.  O JSP renderiza a página HTML, utilizando os dados passados pelo Servlet para exibir informações dinâmicas.
7.  A página HTML renderizada é enviada de volta para o navegador do usuário.

### Configuração e Build

O projeto utiliza o Maven para gerenciar as dependências e o processo de build. O arquivo `pom.xml` define as dependências do projeto (como o driver JDBC do PostgreSQL, a biblioteca de Servlets, etc.) e os plugins necessários para compilar e empacotar a aplicação em um arquivo WAR (Web Application Archive). Este arquivo WAR pode então ser implantado em um servidor Tomcat.

### Fontes e Documentação Oficial

- **Java Servlets**: [https://jakarta.ee/specifications/servlet/](https://jakarta.ee/specifications/servlet/)
- **JavaServer Pages (JSP)**: [https://jakarta.ee/specifications/pages/](https://jakarta.ee/specifications/pages/)
- **JDBC (Java Database Connectivity)**: [https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html)
- **Maven**: [https://maven.apache.org/guides/](https://maven.apache.org/guides/)
- **PostgreSQL JDBC Driver**: [https://jdbc.postgresql.org/documentation/](https://jdbc.postgresql.org/documentation/)
- **java-dotenv**: [https://github.com/cdimascio/java-dotenv](https://github.com/cdimascio/java-dotenv)

