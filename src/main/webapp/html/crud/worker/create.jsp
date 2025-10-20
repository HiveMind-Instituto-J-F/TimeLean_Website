<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>
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
        <h2>ADD WORKER</h2>
        <form action="${pageContext.request.contextPath}/worker/create" method="post">
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" required><br><br>

            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" required><br><br>

            <label for="role">Cargo:</label>
            <input type="text" id="role" name="role" required><br><br>

            <label for="sector">Setor:</label>
            <input type="text" id="sector" name="sector" required><br><br>

            <label for="loginEmail">E-mail de login:</label>
            <input type="email" id="loginEmail" name="loginEmail" required><br><br>

            <label for="loginPassword">Senha:</label>
            <input type="password" id="loginPassword" name="loginPassword" required><br><br>

            <input type="submit" value="Cadastrar">
        </form>
    </body>
</html>
