<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Teste PlanSubscription</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1e2a33;
            color: #f0f0f0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        h1 {
            color: #E89640;
            margin-bottom: 20px;
        }

        form {
            background: #2b3b45;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.25);
            width: 300px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 16px;
            border: none;
            border-radius: 5px;
            background: #1e2a33;
            color: #fff;
            outline: none;
        }

        input:focus {
            border: 2px solid #E89640;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #E89640;
            border: none;
            border-radius: 5px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: 0.2s;
        }

        button:hover {
            background-color: #d4843a;
        }

        .msg {
            margin-top: 15px;
            color: #ff8080;
        }
    </style>
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

    <% 
        String msg = (String) request.getAttribute("msg");
        String error = (String) request.getAttribute("error");
        if (msg != null) { 
    %>
        <div class="msg" style="color: #80ff80;"><%= msg %></div>
    <% } else if (error != null) { %>
        <div class="msg"><%= error %></div>
    <% } %>
</body>
</html>
