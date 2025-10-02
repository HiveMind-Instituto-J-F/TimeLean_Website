<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="../css/header.css"> <!-- CSS para o header -->
    <title>TimeLean - Administração</title>
</head>
<body>
    <header>
            <button id="timelean">
                <img src="img/icons/branding/TIMELEAN.png" alt="Branding Timelean">
                <a href="#hero"></a>
            </button>

            <nav class="inter">
                <a href="" target="_blank">Home</a>
                <a href="" target="_blank">Quem somos</a>
                <a href="" target="_blank">Jurídico</a>
                <a href="" target="_blank">FAQ</a>
            </nav>
            <button class="button small inter">Entrar em contato</button>
    </header>
    <main>
        <h1>Olá, administrador</h1>
        <!-- Aside-Bar abre oportunidades de fluxo para o CRUD, melhorando a organização -->
        <aside>
            <ul>
                <li>Pagamentos</li>
                <li>Planos</li>
                <li>Inscrções de planos</li>
                <li>Empresas</li>
                <li>Plantas Industriais</li>
                <li>Emails de contato</li>
            </ul>
        </aside>

        <!-- Seção para inserção de empresas -->
        <section name="crud_company" class="crud">
            <h2>Adicionar empresa</h2>

            <form action="${pageContext.request.contextPath}/insert_company" method="post">
                <h3>Passo 1 - Adicionar informacoes cadastrais da empresa</h3>
                <label for="company-cnpj">Digite o CNPJ da empresa:</label>
                <input type="text" name="company-cnpj" placeholder="Ex: 123456789123-45">
                <br>
                
                <label for="company-name">Digite o nome da empresa:</label>
                <input type="text" name="company-name">
                <br>

                <label for="company-cnae">Digite o codigo de Classificacao Nacional das Atividades Economicas (CNAE)</label>
                <input type="number" name="company-cnae">
                <br>

                <label for="company-registrant-cpf">Digite o CPF do cliente primario:</label>
                <input type="number" name="company-registrant-cpf">


                <h3>Passo 2 - Adicioanr informacoes para a inscricao do plano</h3>
                <label for="psubscription-start-date">Digite a data de inicio do plano:</label>
                <input type="date" name="psubscription-start-date">
                <br>

                <label for="plan-description">Digite o nome do plano:</label>
                <input type="text" name="plan-description">
                <br>

                <input type="submit">
                <%
                    Integer status = (Integer) request.getAttribute("status");
                    if (status != null) {
                        switch (status) {
                            case 1:
                                out.print("Company and subscription created successfully.");
                                break;
                            case 2:
                                out.print("Subscription creation failed. Company was rolled back.");
                                break;
                            case 3:
                                out.print("Company creation failed.");
                                break;
                            case 4:
                                out.print("No plan selected.");
                                break;
                            default:
                                out.print("Unknown status.");
                        }
                    }
                %>
                <%=request.getAttribute("status")%>
            </form>
        </section>
    </main>
    <footer>

    </footer>
</body>
</html>