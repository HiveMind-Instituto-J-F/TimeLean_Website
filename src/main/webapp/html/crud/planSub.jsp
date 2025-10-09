<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Teste PlanSubscription</title>
    <style>
        .success {
            color: green;
            background: #d4edda;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #c3e6cb;
        }
        .error {
            color: red;
            background: #f8d7da;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <h1>Teste de Inserção - PlanSubscription</h1>

    <%
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;

        if (isLogged == null || !isLogged) {
            //if user not us login return that for login page
            response.sendRedirect("../login.jsp");
            return;
        }
    %>

    <!-- Mensagem de SUCESSO -->
    <% String msg = (String) request.getAttribute("msg"); %>
    <% if (msg != null) { %>
        <div class="success">
            ✅ <%= msg %>
        </div>
    <% } %>

    <!-- Mensagem de ERRO -->
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="error">
             Erro: <%= error %>
        </div>
    <% } %>

    <form action="/HivemindWeb_war/create-plan-subcription" method="post">
        <label for="start_date">Data de Início:</label>
        <input type="date" id="start_date" name="start_date" required>
        <br><br>

        <label for="cnpj_company">CNPJ da Empresa:</label>
        <input type="text" id="cnpj_company" name="cnpj_company" required placeholder="00.000.000/0000-00">
        <br><br>

        <label for="id_plan">ID do Plano:</label>
        <input type="number" id="id_plan" name="id_plan" required min="1">
        <br><br>

        <button type="submit">Enviar</button>
    </form>
</body>
</html>