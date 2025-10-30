<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Criar Plano — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
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

    <div class="form">
        <div>
            <div class="block"></div>
            <h1 class="inter-bold">Criar Plano</h1>
        </div>

        <form class="inter-thin" action="${pageContext.request.contextPath}/plan/create" method="post">
            <div>
                <h3 class="inter-medium">Informações do Plano</h3>

                <div class="input-div">
                    <label for="name">Nome</label>
                    <input type="text" id="name" name="name" placeholder="Insira o nome do plano aqui" required>
                </div>

                <div class="input-div">
                    <label for="description">Descrição</label>
                    <input type="text" id="description" name="description" placeholder="Insira uma breve descrição do plano aqui"></input>
                </div>

                <div class="input-div">
                    <label for="duration">Duração (em dias)</label>
                    <input type="number" id="duration" name="duration" placeholder="Insira a duração do plano aqui" min="1" required>
                </div>

                <div class="input-div">
                    <label for="price">Preço (R$)</label>
                    <input type="text" id="price" name="price" placeholder="Insira o preço do plano aqui" required>
                </div>
            </div>

            <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
            <input class="button submit inter" type="submit" value="Criar">
        </form>
    </div>
</body>
</html>
