<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="hivemind.hivemindweb.models.Plan" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Pagamento</title>
</head>
<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }

    Plan plan = (Plan) request.getAttribute("plan");
%>

<div class="container">
    <h1>Atualizar Plano</h1>

    <form action="<%= request.getContextPath() %>/plan/update" method="post">
        <input type="hidden" name="id" value="<%= plan.getId() %>">
        <input type="number" name="duration" placeholder="Duração em dias" value="<%= plan.getDuration() %>" required>
        <br>
        <input type="decimal" name="price" placeholder="Preço" value="<%= plan.getPrice() %>" required>
        <br>
        <input type="text" name="description" placeholder="Descrição" value="<%= plan.getDescription() %>" required>
        <br>
        <input type="text" name="name" placeholder="Nome do Plano" value="<%= plan.getName() %>" required>
        <br>

        <button type="submit">Atualizar Plano</button>
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
