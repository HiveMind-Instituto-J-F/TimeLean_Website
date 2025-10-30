<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Administração — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/create.css">
</head>
<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }
    String errorMsg = (String) request.getAttribute("errorMessage");
    String msg = (String) request.getAttribute("msg");
%>
<div id="background-img">
    <header class="blur">
        <a href="#background-img" id="timelean">
            <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN.png" alt="TIMELEAN">
        </a>

        <div>
            <nav class="inter navbar">
                <a href="">Home</a>
                <a href="">Quem somos</a>
                <a href="" target="_blank">FAQ</a>
            </nav>
            <a href="" target="_blank" class="button contact inter">Entrar em contato</a>
        </div>
    </header>

    <div>
        <h1 class="inter-bold">Olá, Administrador.</h1>
        <h3 class="inter-medium">Bem vindo ao CRUD.</h3>
    </div>
</div>
<main>
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
    <section>
        <div class="block"></div>
        <h1 class="inter-bold">Adicionar empresa</h1>
        <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
        <div class="inter-medium">
            <form action="${pageContext.request.contextPath}/company/create" method="post">
                <div>
                    <h3>Adicionar informações cadastrais da empresa</h3>

                    <label for="company-name">Nome da empresa</label><br>
                    <input type="text" name="company-name" maxlength="60" required><br>

                    <label for="company-cnpj">CNPJ da empresa</label><br>
                    <input type="text" name="company-cnpj" placeholder="Ex: 17.212.365/0001-82" maxlength="18" required><br>

                    <label for="company-cnae">CNAE (Código de Classificação Nacional das Atividades Econômicas)</label><br>
                    <input type="text" name="company-cnae" placeholder="Ex: 47.89-0/99" required><br>

                    <label for="company-registrant-cpf">CPF do cliente</label><br>
                    <input type="text" name="company-registrant-cpf" placeholder="Ex: 854.969.710-98" maxlength="17" required>
                </div>
                <input class="button submit inter" type="submit">
            </form>
        </div>
    </section>
</main>

<footer class="inter-thin">
    <a href="html/login.jsp">
        <button id="hivemind">
            <img src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
        </button>
    </a>
    <nav class="inter navbar">
        <a href="html/login.html">Home</a>
        <a href="" target="_blank">Quem somos</a>
        <a href="" target="_blank">FAQ</a>
    </nav>
    <nav class="medias">
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/whatsapp.png" alt="whatsapp" class="icon"></a>
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/instagram.png" alt="instagram" class="icon"></a>
    </nav>
    <p class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
</footer>
</body>
</html>
