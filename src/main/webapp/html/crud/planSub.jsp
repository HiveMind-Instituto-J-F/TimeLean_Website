<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Criar PlanSubscription</title>
</head>
<body>
    <%
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;

        if (isLogged == null || !isLogged) {
            //if user not us login return that for login page
            response.sendRedirect("../login.jsp");
            return;
        }
    %>
    <h2>Criar Nova PlanSubscription</h2>

    <form action="/HivemindWeb_war/create-plan-subcription" method="post">
        <label for="start_date">Data de Início:</label><br>
        <input type="date" id="start_date" name="start_date" required><br><br>

        <label for="cnpj_company">CNPJ da Empresa:</label><br>
        <input type="text" id="cnpj_company" name="cnpj_company" required placeholder="00.000.000/0000-00"><br><br>

        <label for="id_plan">ID do Plano:</label><br>
        <input type="number" id="id_plan" name="id_plan" required min="1"><br><br>

        <button type="submit">Enviar</button>
    </form>

    <% 
        String msg = (String) request.getAttribute("msg");
        String error = (String) request.getAttribute("error");
        if (msg != null) { 
    %>
        <p style="color: green;"><%= msg %></p>
    <% } else if (error != null) { %>
        <p style="color: red;"><%= error %></p>
    <% } %>
</body>
</html>