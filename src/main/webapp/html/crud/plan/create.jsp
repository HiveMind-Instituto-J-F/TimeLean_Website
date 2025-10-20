<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Criar Plano</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #4c6ef5 0%, #b197fc 100%);
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
            max-width: 550px;
            transition: 0.3s;
        }

        h1 {
            font-size: 26px;
            color: #333;
            text-align: center;
            margin-bottom: 10px;
        }

        .info {
            background: #eef4ff;
            border: 1px solid #c9d7ff;
            color: #334;
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

        input, textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            transition: 0.2s;
        }

        input:focus, textarea:focus {
            border-color: #4c6ef5;
            box-shadow: 0 0 0 3px rgba(76,110,245,0.15);
            outline: none;
        }

        textarea {
            min-height: 90px;
            resize: vertical;
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
            background: linear-gradient(135deg, #4c6ef5, #9d7cf5);
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
        <h1>📋 Criar Plano</h1>

        <div class="info">
            <strong>Nota:</strong> Preencha todos os campos para criar um novo plano no sistema.
        </div>

        <form method="POST" action="${pageContext.request.contextPath}/create-plan">
            <div class="form-group">
                <label for="name">Nome do Plano *</label>
                <input type="text" id="name" name="name" placeholder="Ex: Plano Premium" required>
            </div>
            <div class="form-group">
                <label for="description">Descrição *</label>
                <textarea id="description" name="description" placeholder="Ex: Inclui todas as funcionalidades..." required></textarea>
            </div>
            <div class="form-group">
                <label for="duration">Duração (em dias) *</label>
                <input type="number" id="duration" name="duration" placeholder="Ex: 30" min="1" required>
            </div>
            <div class="form-group">
                <label for="price">Preço *</label>
                <input type="text" id="price" name="price" placeholder="Ex: 99.99" required>
            </div>
            <button type="submit">Criar Plano</button>
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