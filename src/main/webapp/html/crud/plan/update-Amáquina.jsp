<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.Plan" %>

<%
Plan plan = (Plan) request.getAttribute("plan");
%>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Atualizar Plano — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
</head>

<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }
%>

<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Atualizar Plano</h1>
    </div>

    <form class="inter-thin" action="${pageContext.request.contextPath}/plan/update" method="post">
        <div>
            <h3 class="inter-medium">Informações do Plano</h3>

            <!-- <input type="hidden" name="id" value="<%= plan.getId() %>"> -->

            <div class="input-div">
                <label for="name">Nome do Plano</label>
                <input class="always-orange" type="text" id="name" name="name" value="<%= plan.getName() %>" required>
            </div>

            <div class="input-div">
                <label for="description">Descrição</label>
                <input type="text" id="description" name="description" value="<%= plan.getDescription() %>" required>
            </div>
            
            <div class="input-div">
                <label for="price">Preço</label>
                <input type="text" id="price" name="price" value="<%= plan.getPrice() %>" required>
            </div>

            <div class="input-div">
                <label for="duration">Duração (em dias)</label>
                <input type="number" id="duration" name="duration" value="<%= plan.getDuration() %>" required>
            </div>
        </div>

        <input class="button submit inter" type="submit" value="Atualizar">
    </form>
</div>

</body>
</html>
