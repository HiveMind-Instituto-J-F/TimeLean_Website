<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>

<%
    Worker worker = (Worker) request.getAttribute("worker");
%>

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

    <title>Atualizar Funcionário — TIMELEAN</title>
</head>
<body>
<%
    boolean isLogged = session != null && session.getAttribute("plantCnpj") != null;
    if (!isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/crud/worker/login/login.jsp");
        return;
    }
%>
<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Atualizar Funcionário</h1>
    </div>

    <% if (worker != null) { %>
    <form class="inter-thin" action="<%= request.getContextPath() %>/worker/update" method="post">
        <div>
            <h3 class="inter-medium">Informações do Funcionário</h3>

            <div class="input-div">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" value="<%= worker.getCpf() %>" readonly required>
            </div>

            <div class="input-div">
                <label for="name">Nome</label>
                <input type="text" id="name" name="name" value="<%= worker.getName() %>" required>
            </div>

            <div class="input-div">
                <label for="role">Cargo</label>
                <input type="text" id="role" name="role" value="<%= worker.getRole() %>" required>
            </div>

            <div class="input-div">
                <label for="sector">Setor</label>
                <input type="text" id="sector" name="sector" value="<%= worker.getSector() %>" required>
            </div>

            <div class="input-div">
                <label for="loginEmail">E-mail de Login</label>
                <input type="email" id="loginEmail" name="loginEmail" value="<%= worker.getLoginEmail() %>" required
                pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
            </div>

            <div class="input-div">
                <label for="loginPassword">Senha</label>
                <input type="password" id="loginPassword" name="loginPassword" placeholder="Apenas digite se for mudar a senha">
            </div>

            <input type="hidden" name="oldCpf" value="<%= worker.getCpf() %>">
        </div>

        <input class="button submit inter" type="submit" value="Atualizar">
    </form>
    <% } else { %>
        <p class="inter-medium" style="color:red;">Funcionário não encontrado.</p>
    <% } %>
</div>

</body>
</html>
