# **Documentação**

## Sumário
- [Sistema De Branches](#sistema-de-branches)
- [Estrutura do Projeto Hivemind_Website](#estrutura-do-projeto-hivemind_website)
  - [Descrição da Estrutura do Jakarta EE](#descriçao-da-estrutura-do-jakartee)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Componentes Chave](#componentes-chave)
  - [EnvLoader.java](#envloaderjava)
  - [DBConnection.java](#dbconnectionjava)
  - [AppListener.java](#applistenerjava)
  - [Data Access Objects (DAOs)](#data-access-objects-daos)
  - [Servlets](#servlets)
  - [Modelos (POJOs)](#modelos-pojos)
- [Fluxo de uma Requisição](#fluxo-de-uma-requisição)
- [Configuração e Build](#configuração-e-build)
- [Estrutura De Classes](#estrutura-de-classes)
- [toString](#tostring)
- [Padrão de Código](#padrao-de-codigo)
  - [Convenções de Classes e Métodos](#convenções-de-classes-e-métodos)
  - [Separação De Pacotes](#separaçao-de-pacotes)
  - [Estrutura da Classe Tool (Exemplo)](#estrutura-da-classe-tool-exemplo)
  - [Uso da Classe List](#uso-da-classe-list)
- [Exceções e Logs](#exceções-e-logs)
- [Testes](#testes)
- [Boas Práticas de Commits](#boas-práticas-de-commits)
- [DB](#db)
  - [Classes DAO](#classes-dao)
  - [Uso e Benefícios do Try-with-Resources em Conexões com DB](#uso-e-benefícios-do-try-with-resources-em-conexões-com-db)
- [Fontes e Documentação Oficial](#fontes-e-documentação-oficial)

---

## Sistema de Branches

*   **main**: branch estável, usada para versões finais e releases.
*   **dev**: branch ativa, onde o desenvolvimento e atualizações são feitos continuamente.
*   Fluxo típico:
    1.  Novas features e correções são desenvolvidas na `dev`.
    2.  Quando está estável, o código da `dev` é mesclado na `main`.

---

**OBS**: Essa estrutura é **padrão Maven** para Java Web.

# Estrutura do Projeto Hivemind_Website

```bash
C:.
├───.idea
├───.mvn
│   └───wrapper
├───doc
├───src
│   └───main
│       ├───java
│       │   └───hivemind
│       │       └───hivemindweb
│       │           ├───modules
│       │           ├───Servelets
│       │           └───Tool
│       ├───resources
│       │   └───META-INF
│       └───webapp
│           └───WEB-INF
└───target
    ├───classes
    │   ├───hivemind
    │   │   └───hivemindweb
    │   │       └───Servelets
│   └───META-INF
    ├───generated-sources
    │   └───annotations
    └───HivemindWeb-1.0-SNAPSHOT
        ├───META-INF
        └───WEB-INF
            └───classes
                ├───hivemind
                │   └───hivemindweb
                │       └───Servelets
                └───META-INF
```

---

## Descrição da Estrutura do Jakarta EE

### 2. `.mvn/wrapper`

*   Contém arquivos do **Maven Wrapper**.
*   Permite executar Maven sem instalar globalmente.
*   Scripts: `mvnw` (Linux/macOS) e `mvnw.cmd` (Windows).

### 3. `doc`

*   Para documentação do projeto (ex.: `.md`, diagramas, notas).

### 4. `src/main`

*   Pasta padrão do **Maven** para código-fonte.
*   Subpastas:

    *   **java** → código Java.

        *   `hivemind/hivemindweb/Connection/`: Classes relacionadas à conexão com o banco de dados (`DBConnection.java`).
        *   `hivemind/hivemindweb/DAO/`: Data Access Objects, responsáveis pela interação com o banco de dados para cada entidade (e.g., `AdminDAO.java`, `CompanyDAO.java`).
        *   `hivemind/hivemindweb/Exception/`: Classes de exceção personalizadas para a aplicação (e.g., `InvalidForeignKeyException.java`).
        *   `hivemind/hivemindweb/Servelts/`: Servlets que atuam como controladores, processando requisições HTTP e interagindo com os DAOs e modelos. Organizados por funcionalidade (e.g., `Login/`, `Plan/`, `Worker/`).
        *   `hivemind/hivemindweb/Services/`: Classes de serviço que podem conter lógica de negócio ou utilitários, como `AppListener.java` e `Enums/`.
        *   `hivemind/hivemindweb/config/`: Classes de configuração, como `EnvLoader.java` para carregar variáveis de ambiente.
        *   `hivemind/hivemindweb/models/`: Classes de modelo (POJOs) que representam as entidades do banco de dados (e.g., `Admin.java`, `Company.java`).

    *   **resources** → arquivos que não são código Java, mas são usados pelo app.

        *   `META-INF` → configs, ex.: `persistence.xml` (JPA) ou logging.

    *   **webapp** → arquivos de frontend (HTML, JSP, CSS, JS).

        *   `WEB-INF` → arquivos não acessíveis diretamente pelo navegador.

            *   Ex.: `web.xml` para configurar servlets/filtros.

### 5. `target`

*   Criada pelo **Maven** durante o build.
*   Contém:

    *   **classes** → arquivos `.class` compilados.
    *   **generated-sources/annotations** → código gerado por annotation processors.
    *   **HivemindWeb-1.0-SNAPSHOT** → pacote final do build:

        *   `.war` para web app.
        *   `.jar` para app Java comum.

---

## Tecnologias Utilizadas

O projeto é construído sobre um conjunto de tecnologias padrão da indústria para desenvolvimento web com Java:

-   **Java Servlets**: Para processar requisições e respostas HTTP, atuando como os controladores da aplicação.
-   **JavaServer Pages (JSP)**: Para criar páginas web dinâmicas, atuando como as views.
-   **JDBC (Java Database Connectivity)**: Para conectar e interagir com o banco de dados PostgreSQL.
-   **Maven**: Para gerenciamento de dependências e automação do build.
-   **Dotenv**: Para gerenciamento de variáveis de ambiente, mantendo as configurações sensíveis fora do código-fonte.
-   **HTML, CSS, JavaScript**: Para a estrutura, estilo e interatividade do front-end.

### Componentes Chave

#### `EnvLoader.java`

Esta classe é responsável por carregar as variáveis de ambiente de um arquivo `.env` localizado no diretório `/WEB-INF/`. Ela utiliza a biblioteca `java-dotenv` para ler o arquivo e disponibilizar as variáveis para a aplicação. Isso é crucial para a segurança, pois permite que informações sensíveis, como credenciais de banco de dados, sejam mantidas fora do código-fonte.

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

---

## Estrutura De Classes
O projeto segue a forma básica do POO sendo sempre

- Nome de Classe Maiúscula
- Nome de métodos sempre sendo `verboAção`

## Test Class Env
No pacote **`Test`** existe uma classe dedicada a testes de funções.
Ela é executada em um ambiente isolado e seguro, garantindo que possíveis erros não afetem o restante do sistema.


## toString
O toString deve seguir o seguinte modelo

```java
@Override
    public String toString() {
        return  "=== Dados da Pessoa ===\n" +
                "Nome       : " + nome + "\n" +
                "Idade      : " + idade + " anos\n" +
                "Ativo      : " + ativo + "\n" +
                "Altura     : " + altura + " m\n" +
                "Apelidos   : " + (apelidos != null && apelidos.length > 0 ? Arrays.toString(apelidos) : "Nenhum") + "\n" +
                "Habilidades: " + (habilidades != null && !habilidades.isEmpty() ? String.join(", ", habilidades) : "Nenhuma") + "\n" +
                "=======================";
    }
```



# Padrão de Código

## Convenções de Classes e Métodos

-   **Classes:** PascalCase → `UserController`, `Tool`,
    `DatabaseHelper`\
-   **Métodos:** verbo + ação, camelCase → `createUser()`,
    `calculateScore()`\
-   **Atributos:** camelCase → `userName`, `userEmail`\
-   **Constantes:** UPPER_CASE → `MAX_ATTEMPTS`, `DEFAULT_TIMEOUT`

## Separação De Pacotes
-   **Tool** → contém métodos estáticos reutilizáveis para todo o
    sistema.\
-   **modules** → classes diretas do projeto.\
-   **ExtraPackage**\* → pacotes adicionais conforme necessidade futura,
    ex.: *Analytics, Security, Helpers*.\
    **OBS: Este Package pode mudar de nome dependendo da sua especificação, por exemplo**
    ```bash
      C:.
        └───Tool
          └───Critografy
    ````
-   **Servlets** → servlets que recebem requisições HTTP e chamam
    DAO/Tool/modules.

### Estrutura da Classe Tool (Exemplo)

``` java
package hivemind.hivemindweb.Tool;

public class Tool {

    // Exemplo de função geral
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static String sanitizeInput(String input) {
        return input == null ? "" : input.trim();
    }

    // Outras funções gerais do sistema podem ser adicionadas aqui
}
```

## Uso da Classe List

Para facilitar a locomoção e manipulação de dados é preferível o uso da classe `List<>`.
Dependendo da necessidade, pode ser implementada como `ArrayList` ou `LinkedList`.

### Descrição breve
`List` é uma interface em Java que representa uma coleção ordenada de elementos, permitindo acesso por índice e suporte a elementos duplicados.
Na prática, ela oferece flexibilidade para armazenar e percorrer dados de forma simples e eficiente.


# Boas Práticas de Commits

## Estrutura Recomendada (Conventional Commits)

```
<type>[optional scope]: <short description>
[optional body]
[optional footer(s)]
```

---

## 1️⃣ Tipos de Commits

| Tipo         | Quando usar                                                        |
| ------------ | ------------------------------------------------------------------ |
| **feat**     | Adiciona nova funcionalidade ou recurso                            |
| **fix**      | Corrige bug                                                        |
| **docs**     | Alterações na documentação                                         |
| **style**    | Formatação, espaços, ponto e vírgula, sem alteração de lógica      |
| **refactor** | Refatoração de código sem alterar comportamento                    |
| **perf**     | Alteração que melhora performance                                  |
| **test**     | Adição ou correção de testes                                       |
| **chore**    | Tarefas de manutenção (build, scripts, configuração, dependências) |

---

## 2️⃣ Regras de Boas Práticas

1.  **Mensagem curta e clara**

    *   Primeira linha com resumo de até 50 caracteres.
    *   Evite mensagens genéricas como `Update files` ou `Changes`.

2.  **Use verbo no imperativo**

    *   Ex.: `Add`, `Fix`, `Update`, `Remove`, `Refactor`.
    *   Evite: `Added`, `Fixed`, `I fixed`.

3.  **Commits pequenos e focados**

    *   Um commit = uma alteração/coisa.
    *   Ex.: `Add insert method to PlantasDAO`
        Não misture inserção, update e delete em um commit só.

4.  **Detalhes opcionais**

    *   Linha em branco após o resumo.
    *   Explicação do motivo da mudança e possíveis impactos.

5.  **Escopo opcional**

    *   Entre parênteses, define o módulo ou parte do projeto.
    *   Ex.: `feat(PlantasDAO): add insert method`.

---

## 3️⃣ Exemplos de Commits

```text
feat(PlantasDAO): add insert method using Planta object
```

```text
fix(PlantasDAO): close database connection properly in select
```

```text
refactor(PlantasDAO): update method now accepts Planta object
```

```text
docs(Readme): add project structure and class conventions
```

```text
style(PlantasDAO): format code and fix indentation
```

---

## Dicas Extras

*   Sempre revise o commit antes de enviar: `git diff --staged`.
*   Evite commits automáticos sem descrição.
*   Se necessário, use **footer** para fechar issues ou adicionar notas:

```text
fix(PlantasDAO): handle null values in update method

Closes #42
```

# DB

A O GRUD esta sendo feito com **JDBC** e segue os seguintes padrões de código

## **Classes DAO**

### Retorno de List no select() e Clean Code

Conforme o **Capítulo 7, página 110** do livro **Clean Code (Robert C. Martin)**, **não é recomendado retornar `null`** de métodos de acesso a dados ou coleções. Ao invés disso, sempre retorne uma **coleção vazia** quando não houver resultados. Isso evita `NullPointerException` e facilita a manutenção e testes do código.

No exemplo do `WorkerDAO`, o método `select()` retorna uma `List<Worker>` vazia se nenhum registro for encontrado, seguindo essa recomendação:

```java
public static List<Worker> select() {
    List<Worker> workers = new ArrayList<>(); // sempre inicializada, nunca null
    DBConnection db = new DBConnection();
    String sql = "SELECT * FROM trabalhador";

    try (Connection conn = db.connected();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Worker workerLocal = new Worker(rs.getInt("id")
                // colunas adicionais serão mapeadas futuramente
            );
            workers.add(workerLocal);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        // opcional: log de erro centralizado
    }

    return workers; // nunca retorna null
}
```

---

### Versão Genérica para DAOs

Para padronizar DAOs e reduzir duplicação, é possível criar um **DAO genérico** usando generics e `FunctionalInterface` para mapeamento de resultados. Exemplo simplificado:

```java
public class GenericDAO<T> {

    public static <T> List<T> select(String sql, ResultSetMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapper.map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}
```

Exemplo de uso com `WorkerDAO`:

```java
List<Worker> workers = GenericDAO.select("SELECT * FROM trabalhador", rs -> new Worker(rs.getInt("id")));
```

### Benefícios:

*   Padroniza todos os DAOs.
*   Evita duplicação de código para `select()`.
*   Facilita a manutenção e testes unitários.
*   Mantém compatibilidade com **Clean Code e TDD**.

---

## Uso e Benefícios do Try-with-Resources em Conexões com DB

O **try-with-resources** é uma funcionalidade do Java introduzida no Java 7 que permite **abrir recursos que implementam a interface `AutoCloseable`** e garantir que sejam **fechados automaticamente** ao final do bloco, sem precisar de `finally`.

### Aplicação em DAOs

Em DAOs que utilizam JDBC, **Connection**, **PreparedStatement** e **ResultSet** são recursos que precisam ser fechados para evitar **vazamento de memória** ou conexões pendentes. O try-with-resources permite:

```java
try (Connection conn = db.connected();
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) {
    // lógica de leitura ou manipulação dos dados
} catch (SQLException sqle) {
    sqle.printStackTrace();
}
// Todos os recursos são fechados automaticamente aqui
```

> **OBS:** Documentação oficial do try-with-resources: [Oracle Docs](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)

### Benefícios principais

*   **Fechamento automático de recursos** → elimina a necessidade de `finally` e reduz riscos de vazamento.
*   **Código mais limpo e legível** → menos linhas de código e menos try/catch/finally aninhados.
*   **Segurança** → evita erros por esquecimento de fechar conexões ou statements.
*   **Facilita manutenção e testes** → garante que cada conexão seja encerrada corretamente, mesmo em caso de exceções.

### Fontes e Documentação Oficial

-   **Java Servlets**: [https://jakarta.ee/specifications/servlet/](https://jakarta.ee/specifications/servlet/)
-   **JavaServer Pages (JSP)**: [https://jakarta.ee/specifications/pages/](https://jakarta.ee/specifications/pages/)
-   **JDBC (Java Database Connectivity)**: [https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html)
-   **Maven**: [https://maven.apache.org/guides/](https://maven.apache.org/guides/)
-   **PostgreSQL JDBC Driver**: [https://jdbc.postgresql.org/documentation/](https://jdbc.postgresql.org/documentation/)
-   **java-dotenv**: [https://github.com/cdimascio/java-dotenv](https://github.com/cdimascio/java-dotenv)
- **Clean Code (Robert C. Martin)**: Referência para boas práticas de código.
