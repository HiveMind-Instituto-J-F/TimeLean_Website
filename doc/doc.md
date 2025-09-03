  #**Documentaçao**


  **Sumerio**
- [Sistema De Branches](#sistema-de-branches)
- [Estrutura](#estrutura-do-projeto-hivemind_website)
  - [Descriçao Do JakartEE](#descriçao-da-estrutura-do-jakartee)
- [Estrutura De Classes](#estrutura-de-classes)
- [toString](#tostring)
- [Padrao de Codigo](#padrao-de-codigo)
  - [Convenções de Classes e Métodos](#convenções-de-classes-e-métodos)
  - [Separaçao De Pacotes](#separaçao-de-pacotes)
  - [Estrutura da Classe Tool (Exemplo)](#estrutura-da-classe-tool-exemplo)
  - [Uso da Classe List](#uso-da-classe-list)
- [Exceções e Logs](#exceções-e-logs) Em Breve
- [Testes](#testes) Em Brevem
- [Boas Práticas de Commits](#boas-práticas-de-commits)

  ## Sistema de Branches

  * **main**: branch estável, usada para versões finais e releases.
  * **dev**: branch ativa, onde o desenvolvimento e atualizações são feitos continuamente.
  * Fluxo típico:

    1. Novas features e correções são desenvolvidas na `dev`.
    2. Quando está estável, o código da `dev` é mesclado na `main`.

  ---

  **OBS** Essa estrutura é **padrão Maven** para Java Web. 

  # Estrutura do Projeto Hivemind\_Website

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

  ## Descriçao da Estrutura do JakartEE

  ### 2. `.mvn/wrapper`

  * Contém arquivos do **Maven Wrapper**.
  * Permite executar Maven sem instalar globalmente.
  * Scripts: `mvnw` (Linux/macOS) e `mvnw.cmd` (Windows).

  ### 3. `doc`

  * Para documentação do projeto (ex.: `.md`, diagramas, notas).

  ### 4. `src/main`

  * Pasta padrão do **Maven** para código-fonte.
  * Subpastas:

    * **java** → código Java.

      * `modules` → classes utilitárias.
      * `Servelets` → servlets (tratam requisições HTTP).
      * `Tool` → Funçeos staticas para ultilizaçao geral.
    * **resources** → arquivos que não são código Java, mas são usados pelo app.

      * `META-INF` → configs, ex.: `persistence.xml` (JPA) ou logging.
    * **webapp** → arquivos de frontend (HTML, JSP, CSS, JS).

      * `WEB-INF` → arquivos não acessíveis diretamente pelo navegador.

        * Ex.: `web.xml` para configurar servlets/filtros.

  ### 5. `target`

  * Criada pelo **Maven** durante o build.
  * Contém:

    * **classes** → arquivos `.class` compilados.
    * **generated-sources/annotations** → código gerado por annotation processors.
    * **HivemindWeb-1.0-SNAPSHOT** → pacote final do build:

      * `.war` para web app.
      * `.jar` para app Java comum.

  ---

  ## Estrutura De Classes
  O projeto segue a forma basica do POO sendo sempre

  - Nome de Classe Maiucusla 
  - nomete de metodos sempre sendo `verboAçao`

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

  

  # Padrao de Codigo

  ## Convenções de Classes e Métodos

  -   **Classes:** PascalCase → `UserController`, `Tool`,
      `DatabaseHelper`\
  -   **Métodos:** verbo + ação, camelCase → `createUser()`,
      `calculateScore()`\
  -   **Atributos:** camelCase → `userName`, `userEmail`\
  -   **Constantes:** UPPER_CASE → `MAX_ATTEMPTS`, `DEFAULT_TIMEOUT`

  ## Separaçao De Pacotes
  -   **Tool** → contém métodos estáticos reutilizáveis para todo o
      sistema.\
  -   **modules** → classes diretas do projeto.\
  -   **ExtraPackage**\* → pacotes adicionais conforme necessidade futura,
      ex.: *Analytics, Security, Helpers*.\
      **OBS: Este Packege pode mudar de nome dependo da sua espesificaçao por exemplo**
      ```bash
        C:.
          └───Tool
            └───Critografy
      ````
  -   **Servelets** → servlets que recebem requisições HTTP e chamam
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

1. **Mensagem curta e clara**

  * Primeira linha com resumo de até 50 caracteres.
  * Evite mensagens genéricas como `Update files` ou `Changes`.

2. **Use verbo no imperativo**

  * Ex.: `Add`, `Fix`, `Update`, `Remove`, `Refactor`.
  * Evite: `Added`, `Fixed`, `I fixed`.

3. **Commits pequenos e focados**

  * Um commit = uma alteração/coisa.
  * Ex.: `Add insert method to PlantasDAO`
    Não misture inserção, update e delete em um commit só.

4. **Detalhes opcionais**

  * Linha em branco após o resumo.
  * Explicação do motivo da mudança e possíveis impactos.

5. **Escopo opcional**

  * Entre parênteses, define o módulo ou parte do projeto.
  * Ex.: `feat(PlantasDAO): add insert method`.

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

## 4️⃣ Dicas Extras

* Sempre revise o commit antes de enviar: `git diff --staged`.
* Evite commits automáticos sem descrição.
* Se necessário, use **footer** para fechar issues ou adicionar notas:

```text
fix(PlantasDAO): handle null values in update method

Closes #42
```
s)]