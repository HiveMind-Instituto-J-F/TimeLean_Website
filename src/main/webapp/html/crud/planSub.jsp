<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRUD PlanSubscription</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        h1 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }
        
        .crud-section {
            margin-bottom: 40px;
            padding: 20px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
        }
        
        .crud-section h2 {
            color: #555;
            margin-bottom: 20px;
            font-size: 1.3em;
        }
        
        .success {
            color: green;
            background: #d4edda;
            padding: 15px;
            margin: 15px 0;
            border-radius: 5px;
            border: 1px solid #c3e6cb;
        }
        
        .error {
            color: red;
            background: #f8d7da;
            padding: 15px;
            margin: 15px 0;
            border-radius: 5px;
            border: 1px solid #f5c6cb;
        }
        
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
        }
        
        label {
            font-weight: 600;
            margin-bottom: 5px;
            color: #555;
        }
        
        input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        input:focus {
            outline: none;
            border-color: #4CAF50;
        }
        
        button {
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
            font-weight: 600;
        }
        
        .btn-create {
            background: #4CAF50;
            color: white;
        }
        
        .btn-create:hover {
            background: #45a049;
        }
        
        .btn-update {
            background: #2196F3;
            color: white;
        }
        
        .btn-update:hover {
            background: #0b7dda;
        }
        
        .btn-delete {
            background: #f44336;
            color: white;
        }
        
        .btn-delete:hover {
            background: #da190b;
        }
    </style>
</head>
<body>
    <%
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;

        if (isLogged == null || !isLogged) {
            response.sendRedirect("../login.jsp");
            return;
        }
    %>

    <div class="container">
        <h1>CRUD - PlanSubscription</h1>

        <% String msg = (String) request.getAttribute("msg"); %>
        <% if (msg != null) { %>
            <div class="success">
                ✓ <%= msg %>
            </div>
        <% } %>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="error">
                Erro: <%= error %>
            </div>
        <% } %>

        <div class="crud-section">
            <h2>Criar Nova Assinatura</h2>
            <form action="/HivemindWeb_war/create-plan-subcription" method="post">
                <div class="form-group">
                    <label for="start_date">Data de Início:</label>
                    <input type="date" id="start_date" name="start_date" required>
                </div>

                <div class="form-group">
                    <label for="cnpj_company">CNPJ da Empresa:</label>
                    <input type="text" id="cnpj_company" name="cnpj_company" required placeholder="00.000.000/0000-00">
                </div>

                <div class="form-group">
                    <label for="id_plan">ID do Plano:</label>
                    <input type="number" id="id_plan" name="id_plan" required min="1">
                </div>

                <button type="submit" class="btn-create">Criar Assinatura</button>
            </form>
        </div>

        <div class="crud-section">
            <h2>Atualizar Assinatura</h2>
            <form action="/HivemindWeb_war/update-plan-subcription" method="post">
                <div class="form-group">
                    <label for="update_id">ID da Assinatura:</label>
                    <input type="number" id="update_id" name="id_subscription" required min="1" 
                           placeholder="ID da assinatura a ser atualizada">
                </div>

                <div class="form-group">
                    <label for="update_start_date">Nova Data de Início:</label>
                    <input type="date" id="update_start_date" name="start_date" required>
                </div>

                <div class="form-group">
                    <label for="update_cnpj">Novo CNPJ:</label>
                    <input type="text" id="update_cnpj" name="cnpj_company" required placeholder="00.000.000/0000-00">
                </div>

                <div class="form-group">
                    <label for="update_plan">Novo ID do Plano:</label>
                    <input type="number" id="update_plan" name="id_plan" required min="1">
                </div>

                <button type="submit" class="btn-update">Atualizar Assinatura</button>
            </form>
        </div>

        <div class="crud-section">
            <h2>Deletar Assinatura</h2>
            <form action="/HivemindWeb_war/delete-plan-subcription" method="post" 
                  onsubmit="return confirm('Tem certeza que deseja deletar esta assinatura?');">
                <div class="form-group">
                    <label for="delete_id">ID da Assinatura:</label>
                    <input type="number" id="delete_id" name="id" required min="1" 
                           placeholder="ID da assinatura a ser deletada">
                </div>

                <button type="submit" class="btn-delete">Deletar Assinatura</button>
            </form>
        </div>
    </div>
</body>
</html>