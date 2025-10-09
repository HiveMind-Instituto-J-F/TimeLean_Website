<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Teste PlanSubscription</title>
</head>
<body>
    <h1>Teste de Inserção - PlanSubscription</h1>

    <form action="/HivemindWeb_war/create-plan-subcription" method="post">
        <label for="start_date">Data de Início:</label>
        <input type="date" id="start_date" name="start_date" required>

        <label for="cnpj_company">CNPJ da Empresa:</label>
        <input type="text" id="cnpj_company" name="cnpj_company" required placeholder="00.000.000/0000-00">

        <label for="id_plan">ID do Plano:</label>
        <input type="number" id="id_plan" name="id_plan" required min="1">

        <button type="submit">Enviar</button>
    </form>

     <!-- Displays error message if login fails -->
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div style="color: red; margin-bottom: 15px;" class="inter-thin">
            <%= errorMessage %>
        </div>
    <% } %>
</body>
</html>
wds