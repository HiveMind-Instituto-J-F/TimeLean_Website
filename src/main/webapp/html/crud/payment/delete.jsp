<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Deletar Pagamento</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
            max-width: 450px;
        }
        h1 {
            font-size: 26px;
            color: #333;
            text-align: center;
            margin-bottom: 10px;
        }
        .info {
            background: #fff4f4;
            border: 1px solid #f5c2c7;
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
            border-color: #f093fb;
            box-shadow: 0 0 0 3px rgba(240,147,251,0.15);
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
            background: linear-gradient(135deg, #f093fb, #f5576c);
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
    <h1>❌ Deletar Pagamento</h1>
    <div class="info">
        <strong>Nota:</strong> Informe o ID do pagamento que deseja deletar.
    </div>

    <form method="POST" action="<%= request.getContextPath() %>/delete-payment">
        <div class="form-group">
            <label for="id">ID do Pagamento *</label>
            <input type="number" id="id" name="id" placeholder="Ex: 1" min="1" required>
        </div>
        <button type="submit">Deletar Pagamento</button>
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
