<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/text.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud/form.css">
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/img/icons/favicon/home-v2.png" type="image/x-icon">

    <title>Cadastrar Funcionário — TIMELEAN</title>
</head>
<body>

<%
    boolean isLogged = session != null && session.getAttribute("plantCnpj") != null;
    if (!isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/crud/worker/login/login.jsp");
        return;
    }

    String responsibleCpf = (String) request.getAttribute("responsibleCpf");
    List<Worker> workers = (List<Worker>) request.getAttribute("workers");
%>

<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Cadastrar Funcionário</h1>
    </div>

    <form class="inter-thin" action="<%= request.getContextPath() %>/worker/create" method="post">
        <div>
            <h3 class="inter-medium">Informações do Funcionário</h3>

            <div class="input-div">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" placeholder="000.000.000-00" required>
            </div>

            <div class="input-div">
                <label for="name">Nome</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="input-div">
                <label for="role">Cargo</label>
                <input type="text" id="role" name="role" required>
            </div>

            <div class="input-div">
                <label for="sector">Setor</label>
                <input type="text" id="sector" name="sector" required>
            </div>

            <div class="input-div">
                <label for="loginEmail">E-mail de Login</label>
                <input type="email" id="loginEmail" name="loginEmail" placeholder="exemplo@empresa.com" required
                pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
            </div>

            <div class="input-div">
                <label for="loginPassword">Senha</label>
                <input type="password" id="loginPassword" name="loginPassword" required>
            </div>
        </div>

        <input class="button submit inter" type="submit" value="Cadastrar">
    </form>

    <% if (msg != null) { %>
        <p class="inter-medium" style="color:green;"><%= msg %></p>
    <% } else if (errorMsg != null) { %>
        <p class="inter-medium" style="color:red;">Erro: <%= errorMsg %></p>
    <% } %>
</div>

</body>
</html>
