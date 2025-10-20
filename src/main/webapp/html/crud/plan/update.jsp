<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Pagamento</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px;
        }

        .container {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
            padding: 40px 50px;
            width: 100%;
            max-width: 500px;
            transition: 0.3s;
        }

        h1 {
            font-size: 26px;
            color: #333;
            text-align: center;
            margin-bottom: 10px;
        }

        .info {
            background: #fff4e5;
            border: 1px solid #ffd6a5;
            color: #333;
            padding: 12px;
            border-radius: 6px;
            font-size: 14px;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            font-size: 14px;
            color: #333;
            font-weight: 600;
            display: block;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            transition: 0.2s;
        }

        input:focus {
            border-color: #ff9a9e;
            box-shadow: 0 0 0 3px rgba(255,154,158,0.15);
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            font-size: 15px;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            color: white;
            cursor: pointer;
            transition: 0.2s;
            background: linear-gradient(135deg, #ff9a9e, #fad0c4);
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(0, 0, 0, 0.15);
        }

        .message {
            margin-top: 20px;
            padding: 15px;
            border-radius: 6px;
            font-size: 14px;
            text-align: center;
            font-weight: 600;
        }

        .message.success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .message.error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
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
    <h1>💳 Atualizar Pagamento</h1>

    <div class="info">
        <strong>Nota:</strong> Preencha os campos abaixo para atualizar um pagamento existente.
    </div>

    <form method="POST" action="<%= request.getContextPath() %>/update-payment">
        <div class="form-group">
            <label for="id">ID do Pagamento *</label>
            <input type="number" id="id" name="id" placeholder="Ex: 1" min="1" required>
        </div>
        <div class="form-group">
            <label for="amount">Valor do Pagamento *</label>
            <input type="text" id="amount" name="amount" placeholder="Ex: 99.99" required>
        </div>
        <div class="form-group">
            <label for="method">Método de Pagamento *</label>
            <input type="text" id="method" name="method" placeholder="Ex: Cartão de Crédito" required>
        </div>
        <div class="form-group">
            <label for="date">Data do Pagamento *</label>
            <input type="date" id="date" name="date" required>
        </div>
        <button type="submit">Atualizar Pagamento</button>
    </form>

    <%
        String msg = (String) request.getAttribute("msg");
        String error = (String) request.getAttribute("error");

        if (msg != null && !msg.isEmpty()) {
    %>
        <div class="message success"><%= msg %></div>
    <%
        }
        if (error != null && !error.isEmpty()) {
    %>
        <div class="message error"><%= error %></div>
    <%
        }
    %>
</div>
</body>
</html>
