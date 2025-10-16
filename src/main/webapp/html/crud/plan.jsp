<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Planos</title>
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
        
        h1, h2 {
            color: #333;
            margin-bottom: 25px;
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

        hr {
            margin: 40px 0;
            border: none;
            border-top: 2px dashed #ddd;
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
        <h1>📋 Criar Novo Plano</h1>
        
        <div class="info">
            <strong>Nota:</strong> Este é um formulário de teste para o servlet <code>CreatePlan</code> e <code>Delete</code>.
        </div>
        
        <!-- Formulário de Criação -->
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

        <hr>

        <!-- Formulário de Exclusão -->
        <h2>🗑️ Deletar Plano</h2>
        <form method="POST" action="${pageContext.request.contextPath}/delete-plan">
            <div class="form-group">
                <label for="id">ID do Plano *</label>
                <input type="number" id="id" name="id" placeholder="Ex: 1" required>
            </div>
            <button type="submit" style="background: linear-gradient(135deg, #ff5858 0%, #f09819 100%);">Deletar Plano</button>
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
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Planos</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
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
        h1, h2 { color: #333; margin-bottom: 25px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input, textarea {
            width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 4px;
            font-size: 14px; font-family: Arial, sans-serif; transition: border-color 0.3s;
        }
        input:focus, textarea:focus {
            outline: none; border-color: #667eea; box-shadow: 0 0 0 3px rgba(102,126,234,0.1);
        }
        textarea { resize: vertical; min-height: 100px; }
        button {
            width: 100%; padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white; border: none; border-radius: 4px;
            font-size: 16px; font-weight: bold; cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        button:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(102,126,234,0.4); }
        button:active { transform: translateY(0); }
        .message {
            margin-top: 20px; padding: 15px; border-radius: 4px;
            text-align: center; display: none;
        }
        .message.success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; display: block; }
        .message.error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; display: block; }
        .info {
            background-color: #e7f3ff; border: 1px solid #b3d9ff; color: #004085;
            padding: 15px; border-radius: 4px; margin-bottom: 20px; font-size: 14px;
        }
        hr { margin: 40px 0; border: none; border-top: 2px dashed #ddd; }
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
        <h1>📋 Gerenciar Planos</h1>
        
        <div class="info">
            <strong>Nota:</strong> Este é um formulário de teste para os servlets <code>CreatePlan</code>, <code>Delete</code> e <code>Update</code>.
        </div>
        
        <!-- Formulário de Criação -->
        <form method="POST" action="${pageContext.request.contextPath}/create-plan">
            <h2>🆕 Criar Plano</h2>
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

        <hr>

        <!-- Formulário de Exclusão -->
        <h2>🗑️ Deletar Plano</h2>
        <form method="POST" action="${pageContext.request.contextPath}/delete-plan">
            <div class="form-group">
                <label for="id">ID do Plano *</label>
                <input type="number" id="id" name="id" placeholder="Ex: 1" required>
            </div>
            <button type="submit" style="background: linear-gradient(135deg, #ff5858 0%, #f09819 100%);">Deletar Plano</button>
        </form>

        <hr>

        <!-- Formulário de Atualização -->
        <h2>✏️ Atualizar Plano</h2>
        <form method="POST" action="${pageContext.request.contextPath}/update-plan">
            <div class="form-group">
                <label for="updateName">Nome do Plano *</label>
                <input type="text" id="updateName" name="name" placeholder="Ex: Plano Premium" required>
            </div>
            <div class="form-group">
                <label for="id">ID: *</label>
                <input type="text" id="id" name="id" placeholder="Ex: 1" required>
            </div>
            <div class="form-group">
                <label for="updateDescription">Nova Descrição *</label>
                <textarea id="updateDescription" name="description" placeholder="Ex: Agora com novas funções..." required></textarea>
            </div>
            <div class="form-group">
                <label for="updateDuration">Nova Duração (em dias) *</label>
                <input type="number" id="updateDuration" name="duration" placeholder="Ex: 60" min="1" required>
            </div>
            <div class="form-group">
                <label for="updatePrice">Novo Preço *</label>
                <input type="text" id="updatePrice" name="price" placeholder="Ex: 129.99" required>
            </div>
            <button type="submit" style="background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);">Atualizar Plano</button>
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
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
