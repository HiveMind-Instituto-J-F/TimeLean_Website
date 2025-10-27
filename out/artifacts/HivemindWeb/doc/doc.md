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
  - [Exceções Customizadas](#exceções-customizadas)
- [Testes](#testes)
  - [TDDEnv.java](#tddenvjava)
- [Serviços de Autenticação (AuthService)](#serviços-de-autenticação-authservice)
- [Enums (FilterType)](#enums-filtertype)
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

**(Last Update 18/10/2025)**
```bash
D:.
├───.idea
│   └───artifacts
├───.mvn
│   └───wrapper
├───.vscode
├───doc
├───Drivers
├───src
│   ├───main
│   │   ├───java
│   │   │   ├───hivemind
│   │   │   │   └───hivemindweb
│   │   │   │       ├───AuthService
│   │   │   │       ├───config
│   │   │   │       ├───Connection
│   │   │   │       ├───DAO
│   │   │   │       ├───Exception
│   │   │   │       ├───models
│   │   │   │       ├───Servelts
│   │   │   │       │   ├───Company
│   │   │   │       │   │   └───update
│   │   │   │       │   ├───Login
│   │   │   │       │   ├───Payment
│   │   │   │       │   ├───Plan
│   │   │   │       │   ├───PlanSubscription
│   │   │   │       │   ├───Plant
│   │   │   │       │   └───Worker
│   │   │   │       │       ├───login
│   │   │   │       │       └───Update
│   │   │   │       └───Services
│   │   │   │           └───Enums
│   │   │   └───test
│   │   ├───resources
│   │   │   └───META-INF
│   │   └───webapp
│   │       ├───css
│   │       │   ├───crud
│   │       │   ├───land
│   │       │   └───others
│   │       ├───html
│   │       │   ├───crud
│   │       │   │   ├───company
│   │       │   │   │   └───error
│   │       │   │   ├───plant
│   │       │   │   └───worker
│   │       │   │       └───login
│   │       │   └───error
│   │       ├───img
│   │       │   ├───assets
│   │       │   │   ├───fotos
│   │       │   │   ├───ods
│   │       │   │   └───pictures
│   │       │   ├───background
│   │       │   ├───favicon
│   │       │   ├───fotos
│   │       │   ├───icons
│   │       │   │   ├───branding
│   │       │   │   ├───demo
│   │       │   │   ├───favicon
│   │       │   │   ├───social
│   │       │   │   └───ui
│   │       │   └───ods
│   │       ├───js
│   │       └───WEB-INF
│   └───test
│       └───java
└───target
    ├───classes
    │   ├───hivemind
    │   │   └───hivemindweb
    │   │       ├───AuthService
    │   │       ├───config
    │   │       ├───Connection
    │   │       ├───DAO
    │   │       ├───Exception
    │   │       ├───models
    │   │       ├───Servelts
    │   │       │   ├───Company
    │   │       │   │   └───update
    │   │       │   ├───Login
    │   │       │   ├───Payment
    │   │       │   ├───Plan
    │   │       │   ├───PlanSubscription
    │   │       │   ├───Plant
    │   │       │   └───Worker
    │   │       │       ├───login
    │   │       │       └───Update
    │   │       └───Services
    │   │           └───Enums
    │   ├───META-INF
    │   └───test
    ├───generated-sources
    │   └───annotations
    ├───HivemindWeb-1.0-SNAPSHOT
    │   ├───css
    │   │   ├───land
    │   │   └───others
    │   ├───html
    │   │   └───crud
    │   │       └───worker
    │   │           └───login
    │   ├───img
    │   │   ├───assets
    │   │   │   ├───fotos
    │   │   │   ├───ods
    │   │   │   └───pictures
    │   │   ├───background
    │   │   └───icons
    │   │       ├───branding
    │   │       ├───demo
    │   │       ├───favicon
    │   │       ├───social
    │   │       └───ui
    │   ├───js
    │   ├───META-INF
    │   └───WEB-INF
    │       ├───classes
    │       │   ├───hivemind
    │       │   │   └───hivemindweb
    │       │   │       ├───AuthService
    │       │   │       ├───config
    │       │   │       ├───Connection
    │       │   │       ├───DAO
    │       │   │       ├───Exception
    │       │   │       ├───models
    │       │   │       ├───Servelts
    │       │   │       │   ├───Login
    │       │   │       │   ├───Plan
    │       │   │       │   ├───PlanSubscription
    │       │   │       │   ├───Plant
    │       │   │       │   └───Worker
    │       │   │       │       ├───login
    │       │   │       │       └───Update
    │       │   │       └───Services
    │       │   ├───META-INF
    │       │   └───test
    │       └───lib
    └───test-classes
```

---

## Descrição da Estrutura do Jakarta EE

# MVE (Model-View-Entity) - Jakarta EE

O **MVE** é uma forma simples de organizar projetos web, parecida com o **MVC**, mas com foco em entidades de negócio. Ele separa a aplicação em três partes principais:

## 1. Model (Modelo)
- Representa os dados e a lógica de negócio.
- Exemplo: classes `Worker`, `Plan`, `Company` que definem regras e atributos.

## 2. View (Visão)
- Tudo que o usuário vê e interage, geralmente HTML, CSS e JS.
- Exemplo: páginas em `webapp/html` ou arquivos de front-end.

## 3. Entity (Entidade)
- Representa objetos do banco de dados e seu mapeamento.
- Exemplo: classes que mapeiam tabelas via JPA ou DAO (`models` e `DAO`).

---

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

        *   `AuthService/`: Serviço de autenticação (`AuthService.java`).
        *   `Connection/`: Classes relacionadas à conexão com o banco de dados (`DBConnection.java`).
        *   `DAO/`: Data Access Objects, responsáveis pela interação com o banco de dados para cada entidade.
        *   `Exception/`: Classes de exceção personalizadas para a aplicação.
        *   `Servelts/`: Servlets que atuam como controladores, processando requisições HTTP e interagindo com os DAOs e modelos.
        *   `Services/`: Classes de serviço que podem conter lógica de negócio ou utilitários, como `AppListener.java` e `Enums/`.
        *   `config/`: Classes de configuração, como `EnvLoader.java` para carregar variáveis de ambiente.
        *   `models/`: Classes de modelo (POJOs) que representam as entidades do banco de dados.

    *   **resources** → arquivos que não são código Java, mas são usados pelo app.

        *   `META-INF` → configs, ex.: `persistence.xml` (JPA) ou logging.

    *   **webapp** → arquivos de frontend (HTML, JSP, CSS, JS).

        *   `WEB-INF` → arquivos não acessíveis diretamente pelo navegador.

            *   Ex.: `web.xml` para configurar servlets/filtros.

**OBS: POJOs, ou Plain Old Java Objects, são classes simples em Java que não dependem de nenhum framework ou biblioteca externa**

### 5. `target`

*   Criada pelo **Maven** durante o build.
*   Contém:

    *   **classes** → arquivos `.class` compilados.
    *   **generated-sources/annotations** → código gerado por annotation processors.
    *   **HivemindWeb-1.0-SNAPSHOT** → pacote final do build:

        *   `.war` para web app.
        *   `.jar` para app Java comum.

---
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

### Configuração e Build

O projeto utiliza o Maven para gerenciar as dependências e o processo de build. O arquivo `pom.xml` define as dependências do projeto (como o driver JDBC do PostgreSQL, a biblioteca de Servlets, etc.) e os plugins necessários para compilar e empacotar a aplicação em um arquivo WAR (Web Application Archive). Este arquivo WAR pode então ser implantado em um servidor Tomcat.

**OBS: Driver Do Postgree Se encotra no diretorio `Drivers` e tem que ser linkado no habiente de desenvolvimento**
---

## Arquivos de Frontend (HTML, CSS, JavaScript)

O diretório `src/main/webapp` contém todos os recursos estáticos e dinâmicos do frontend da aplicação.

### `html/` e Páginas JSP

Este diretório contém os arquivos `.html` e `.jsp` que compõem a interface do usuário. As páginas `.jsp` (JavaServer Pages) são arquivos HTML com a capacidade de incorporar código Java, permitindo a criação de conteúdo dinâmico. Elas funcionam como a camada de *View* no padrão MVC, recebendo dados dos Servlets e renderizando a página final para o navegador do cliente.
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

## Regras de Boas Práticas

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

## 3Exemplos de Commits

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

```text
fix(PlantasDAO): handle null values in update method

Closes #42
```

## Exceções e Logs

### Exceções Customizadas

O projeto define algumas exceções customizadas para lidar com cenários específicos da aplicação de forma mais granular e semântica. Essas exceções estendem `java.lang.Exception` ou `java.lang.RuntimeException` e são utilizadas para sinalizar condições de erro que a aplicação pode tratar ou que indicam falhas específicas.

#### `SessionExpiredException.java`

Esta exceção é lançada quando uma sessão de usuário expira ou é considerada inválida. Ela é crucial para a segurança da aplicação, garantindo que usuários não autorizados ou sessões inativas não possam continuar acessando recursos protegidos. A captura e tratamento desta exceção geralmente redireciona o usuário para a página de login.

```java
package hivemind.hivemindweb.Exception;

public class SessionExpiredException extends Exception {
    public SessionExpiredException(String message) {
        super(message);
    }
}
```

#### `InvalidForeignKeyException.java`

Esta exceção é utilizada para indicar que uma operação no banco de dados falhou devido a uma violação de chave estrangeira. Isso ocorre quando há uma tentativa de inserir ou atualizar dados que fazem referência a um registro inexistente em outra tabela, ou de excluir um registro que ainda é referenciado por outros. Ajuda a manter a integridade referencial do banco de dados.

#### `InvalidPrimaryKeyException.java`

Lançada quando uma operação no banco de dados tenta violar a restrição de chave primária, como a inserção de um registro com um ID já existente. Esta exceção garante a unicidade dos registros identificados por suas chaves primárias.

## Testes

No projeto, a abordagem de testes é fundamental para garantir a qualidade e a funcionalidade do código. Embora a documentação detalhada sobre testes ainda esteja em desenvolvimento, a presença de uma classe específica para ambiente de testes demonstra a preocupação com a validação do sistema.

### `TDDEnv.java`

Esta classe, localizada no pacote `test`, é dedicada a configurar um ambiente isolado para a execução de testes. A sigla TDD (Test-Driven Development) sugere que o desenvolvimento é guiado por testes, onde os testes são escritos antes do código de produção. O objetivo de `TDDEnv.java` é prover as condições necessárias para que os testes possam ser executados de forma independente e segura, sem afetar o ambiente de produção ou depender de configurações externas que não estejam sob controle do teste.

```java
// Exemplo simplificado de TDDEnv.java
package test;

import hivemind.hivemindweb.config.EnvLoader;
import jakarta.servlet.ServletContext;

public class TDDEnv {

    public static void setupTestEnvironment(ServletContext servletContext) {
        // Inicializa o EnvLoader para carregar variáveis de ambiente de teste
        EnvLoader.init(servletContext);
        // Outras configurações específicas para o ambiente de teste
        System.out.println("[INFO] Ambiente de teste configurado.");
    }

    public static void teardownTestEnvironment() {
        // Limpeza do ambiente após os testes
        System.out.println("[INFO] Ambiente de teste desconfigurado.");
    }
}
```



**Benefícios de um Ambiente de Teste Dedicado:**

*   **Isolamento**: Garante que os testes não interfiram nos dados ou configurações do ambiente de desenvolvimento/produção.
*   **Reprodutibilidade**: Permite que os testes sejam executados múltiplas vezes com os mesmos resultados esperados.
*   **Automação**: Facilita a integração contínua e a execução automática de testes.
*   **Segurança**: Evita que erros em testes causem problemas no sistema principal.

## Logs

No projeto, os logs seguem o seguinte padrão de mensagens:

| Nível de Log | Descrição |
|--------------|-----------|
| `[ERROR]`    | Erros graves que impactam diretamente o usuário ou o sistema, como `NullPointerException` ou falhas de execução. |
| `[WARN]`     | Avisos importantes, indicando possíveis problemas, como dados ausentes ou falhas ao acessar informações no banco de dados. |
| `[INFO]`     | Informações gerais ou mensagens de depuração (*debug*), úteis para acompanhar o fluxo do sistema sem indicar problemas. |

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

    public static <T> List<T> select(String sql) {
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

#### `AuthService.java`

#### Enums

##### `FilterType.java`

Este `enum` define os tipos de filtros que podem ser aplicados em alguma funcionalidade da aplicação, como na filtragem de dados ou na validação de inputs. Atualmente, ele define dois tipos:

-   `INPUT_TEXT`: Representa um filtro aplicado a campos de texto.
-   `INPUT_OPTION`: Representa um filtro aplicado a opções de seleção (dropdowns, radio buttons, etc.).

```java
package hivemind.hivemindweb.Services.Enums;

public enum FilterType {
    INPUT_TEXT, INPUT_OPTION
}
```



Esta classe é responsável por gerenciar a lógica de autenticação de usuários, especificamente para administradores. Ela utiliza a biblioteca `jBCrypt` para realizar a hash de senhas e a verificação de credenciais de forma segura. O método `login()` compara a senha fornecida pelo usuário com a senha armazenada (hashed) no banco de dados, enquanto o método `hash()` gera uma nova hash para uma senha.

### Fontes e Documentação Oficial

#### Servlets e JSP
- **Java Servlets**: [https://jakarta.ee/specifications/servlet/](https://jakarta.ee/specifications/servlet/)
- **JavaServer Pages (JSP)**: [https://jakarta.ee/specifications/pages/](https://jakarta.ee/specifications/pages/)
- **Jakarta Servlet Tutorial**: [https://jakarta.ee/learn/docs/jakartaee-tutorial/current/web/servlets/servlets.html](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/web/servlets/servlets.html)
- **ServletContextListener API Documentation**: [https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/servletcontextlistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/servletcontextlistener)
- **ServletContextListener Example (Mkyong)**: [https://mkyong.com/servlet/what-is-listener-servletcontextlistener-example/](https://mkyong.com/servlet/what-is-listener-servletcontextlistener-example/)
- **Servlet - Context Event and Context Listener (GeeksforGeeks)**: [https://www.geeksforgeeks.org/java/servlet-context-event-and-context-listener/](https://www.geeksforgeeks.org/java/servlet-context-event-and-context-listener/)

#### Banco de Dados e Dependências
- **JDBC (Java Database Connectivity)**: [https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html)
- **PostgreSQL JDBC Driver**: [https://jdbc.postgresql.org/documentation/](https://jdbc.postgresql.org/documentation/)
- **Maven**: [https://maven.apache.org/guides/](https://maven.apache.org/guides/)
- **java-dotenv**: [https://github.com/cdimascio/java-dotenv](https://github.com/cdimascio/java-dotenv)

#### Enums
- **Enum (Java Platform SE 8)**: [https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html](https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html)
- **Enums (Oracle Java Tutorials)**: [https://docs.oracle.com/javase/8/docs/technotes/guides/language/enums.html](https://docs.oracle.com/javase/8/docs/technotes/guides/language/enums.html)
- **A Guide to Java Enums (Baeldung)**: [https://www.baeldung.com/a-guide-to-java-enums](https://www.baeldung.com/a-guide-to-java-enums)
- **Java Enums (W3Schools)**: [https://www.w3schools.com/java/java_enums.asp](https://www.w3schools.com/java/java_enums.asp)
- **How to make the most of Java enums (Oracle Java Magazine)**: [https://blogs.oracle.com/javamagazine/post/how-to-make-the-most-of-java-enums](https://blogs.oracle.com/javamagazine/post/how-to-make-the-most-of-java-enums)

#### Boas Práticas
- **Clean Code (Robert C. Martin)**: Referência para boas práticas de código
