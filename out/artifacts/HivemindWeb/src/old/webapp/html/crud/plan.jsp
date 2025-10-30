<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Planos</title>
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

        h2 {
            font-size: 18px;
            color: #444;
            margin: 25px 0 15px;
            border-left: 4px solid #4c6ef5;
            padding-left: 10px;
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
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(0, 0, 0, 0.15);
        }

        /* Gradientes por tipo */
        .btn-create { background: linear-gradient(135deg, #4c6ef5, #9d7cf5); }
        .btn-delete { background: linear-gradient(135deg, #f44336, #ff9800); }
        .btn-update { background: linear-gradient(135deg, #43a047, #a8e063); }
        .btn-active { background: linear-gradient(135deg, #2196f3, #21cbf3); }

        hr {
            border: none;
            border-top: 1px dashed #ddd;
            margin: 30px 0;
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
        <h1>📋 Gerenciar Planos</h1>

        <div class="info">
            <strong>Nota:</strong> Utilize os formulários abaixo para testar os servlets <code>CreatePlan</code>, <code>Delete</code>, <code>Update</code> e <code>Active</code>.
        </div>

        <!-- Criar Plano -->
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
            <button type="submit" class="btn-create">Criar Plano</button>
        </form>

        <hr>

        <!-- Deletar Plano -->
        <form method="POST" action="${pageContext.request.contextPath}/delete-plan">
            <h2>🗑️ Deletar Plano</h2>
            <div class="form-group">
                <label for="deleteId">ID do Plano *</label>
                <input type="number" id="deleteId" name="id" placeholder="Ex: 1" required>
            </div>
            <button type="submit" class="btn-delete">Deletar Plano</button>
        </form>

        <hr>

        <!-- Atualizar Plano -->
        <form method="POST" action="${pageContext.request.contextPath}/update-plan">
            <h2>✏️ Atualizar Plano</h2>
            <div class="form-group">
                <label for="updateId">ID *</label>
                <input type="number" id="updateId" name="id" placeholder="Ex: 1" required>
            </div>
            <div class="form-group">
                <label for="updateName">Nome *</label>
                <input type="text" id="updateName" name="name" placeholder="Ex: Plano Avançado" required>
            </div>
            <div class="form-group">
                <label for="updateDescription">Descrição *</label>
                <textarea id="updateDescription" name="description" placeholder="Ex: Agora com novos recursos..." required></textarea>
            </div>
            <div class="form-group">
                <label for="updateDuration">Duração (dias) *</label>
                <input type="number" id="updateDuration" name="duration" placeholder="Ex: 60" required>
            </div>
            <div class="form-group">
                <label for="updatePrice">Preço *</label>
                <input type="text" id="updatePrice" name="price" placeholder="Ex: 129.99" required>
            </div>
            <button type="submit" class="btn-update">Atualizar Plano</button>
        </form>

        <hr>

        <!-- Ativar Plano -->
        <form method="POST" action="${pageContext.request.contextPath}/active-plan">
            <h2>✅ Ativar Plano</h2>
            <div class="form-group">
                <label for="duration">ID do Plano *</label>
                <input type="number" id="id" name="id" placeholder="Ex: 1" required>
            </div>
            <button type="submit" class="btn-active">Ativar Plano</button>
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
