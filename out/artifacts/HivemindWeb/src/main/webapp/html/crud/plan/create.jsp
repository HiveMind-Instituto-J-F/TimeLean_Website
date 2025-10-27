<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Criar Plano</title>
</head>
<body>
    <%
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
        if (isLogged == null || !isLogged) {
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");
            return;
        }
    %>

    <div class="container">
        <h1>Criar Plano</h1>

        <form action="${pageContext.request.contextPath}/plan/create" method="post">
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