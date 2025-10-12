<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teste - Criar Plano</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 100%;
            max-width: 500px;
        }
        
        h1 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: bold;
        }
        
        input, textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            font-family: Arial, sans-serif;
            transition: border-color 0.3s;
        }
        
        input:focus, textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        
        button {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        button:active {
            transform: translateY(0);
        }
        
        .message {
            margin-top: 20px;
            padding: 15px;
            border-radius: 4px;
            text-align: center;
            display: none;
        }
        
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            display: block;
        }
        
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            display: block;
        }
        
        .info {
            background-color: #e7f3ff;
            border: 1px solid #b3d9ff;
            color: #004085;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 14px;
        }
    </style>
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
    <div class="container">
        <h1>📋 Criar Novo Plano</h1>
        
        <div class="info">
            <strong>Nota:</strong> Este é um formulário de teste para o servlet CreatePlan. Preencha todos os campos corretamente.
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
            
            if(msg != null && !msg.isEmpty()) {
        %>
            <div class="message success"><%= msg %></div>
        <%
            }
            if(error != null && !error.isEmpty()) {
        %>
            <div class="message error"><%= error %></div>
        <%
            }
        %>
    </div>
</body>
</html>