#**Documentaçao**


**Sumerio**
- [Sistema De Branches](#sistema-de-branches)
- [Estrutura](#estrutura-do-projeto-hivemind_website)
    - [Descriçao Do JakartEE](#descriçao-da-estrutura-do-jakartee)
- [Estrutura De Classes](#estrutura-de-classes)
- [toString](#tostring)

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

# Estrutura De Classes
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


