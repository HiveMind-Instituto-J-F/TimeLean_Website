<p align="center">
	<img src="src/main/webapp/img/icons/branding/TIMELEAN%20%28black%29.png](https://timeleanwebsite-production.up.railway.app/img/icons/branding/TIMELEAN%20(black).png" alt="Hivemind" width="200" />
</p>

# TimeLean — Backend

Este repositório contém o backend do projeto TimeLean, desenvolvido pelo grupo Hivemind como parte do trabalho interdisciplinar do Instituto J&F.

No Linux / macOS:

```bash
./mvnw clean package
```

O WAR gerado ficará em `target/`. Faça o deploy em um Tomcat (diretório `webapps/`) ou use o `Dockerfile` para criar uma imagem do projeto.

## Executando / Implantação

- Deploy direto: copie o WAR para o Tomcat na pasta `webapps/` e inicie o container.
- Docker: adapte o `Dockerfile` conforme ambiente e construa a imagem com `docker build -t timelean .`.

## Contribuição

Contribuições são bem-vindas. Para alterações maiores, abra uma issue descrevendo a proposta e submeta um pull request com testes e documentação atualizada.

## Licença

MIT License


---
