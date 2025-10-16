<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>

<%
Worker worker = (Worker) request.getAttribute("worker");
%>

<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="../css/style.css">
        <link rel="stylesheet" href="../css/text.css">
        <link rel="stylesheet" href="../css/others/login.css">

        <title>TIMELEAN</title>
        <link rel="shortcut icon" href="../img/favicon/home-v2.png" type="image/x-icon">
    </head>
    <body>
        <h1>Relacao WORKER</h1>
        <h2>UPDATE WORKER</h2>
        <form action="${pageContext.request.contextPath}/worker/update" method="post">
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" value="<%= worker.getCpf() %>" required readonly><br><br>

            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" value="<%= worker.getName() %>" required><br><br>

            <label for="role">Cargo:</label>
            <input type="text" id="role" name="role" value="<%= worker.getRole() %>" required><br><br>

            <label for="sector">Setor:</label>
            <input type="text" id="sector" name="sector" value="<%= worker.getSector() %>" required><br><br>

            <label for="loginEmail">E-mail de login:</label>
            <input type="email" id="loginEmail" name="loginEmail" value="<%= worker.getLoginEmail() %>" required><br><br>

            <label for="loginPassword">Senha:</label>
            <input type="password" id="loginPassword" name="loginPassword" placeholder="Apenas digite se for mudar a senha"><br><br>

            <input type="submit" value="Cadastrar">
        </form>
    </body>
</html>