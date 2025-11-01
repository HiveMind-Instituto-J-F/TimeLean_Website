<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.PlanSubscription" %>
<%@ page pageEncoding="UTF-8" %>

<%
    // Verifica login
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }

    // Obtém o objeto do request (pode vir nulo)
    PlanSubscription planSubscription = (PlanSubscription) request.getAttribute("planSubscription");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Atualizar Assinatura de Plano — TIMELEAN</title>
</head>

<body>

<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Atualizar Assinatura de Plano</h1>
    </div>

    <form class="inter-thin" action="${pageContext.request.contextPath}/plan_subscription/update" method="post">
        <input type="hidden" name="id" value="<%= (planSubscription != null) ? planSubscription.getId() : "" %>">

        <div>
            <h3 class="inter-medium">Informações da Assinatura</h3>

            <div class="input-div">
                <label for="id_plan">ID do Plano</label>
                <input type="number" id="id_plan" name="id_plan" min="1"
                       value="<%= (planSubscription != null) ? planSubscription.getIdPlan() : "" %>" readonly>
            </div>

            <div class="input-div">
                <label for="cnpj_company">CNPJ da Empresa</label>
                <input type="text" id="cnpj_company" name="cnpj_company" pattern="^[0-9]{14}$"
                       value="<%= (planSubscription != null) ? planSubscription.getCnpjCompany() : "" %>" readonly>
            </div>

            <div class="input-div">
                <label for="start_date">Data de Início</label>
                <input type="date" id="start_date" name="start_date"
                       value="<%= (planSubscription != null && planSubscription.getStartDate() != null) ? planSubscription.getStartDate() : "" %>"
                       required>
            </div>

            <div class="input-div">
                <label for="number_installments">Número de Parcelas</label>
                <input type="number" id="number_installments" name="number_installments" min="1"
                       value="<%= (planSubscription != null) ? planSubscription.getNumberInstallments() : "" %>" readonly>
            </div>
        </div>

        <input class="button submit inter" type="submit" value="Atualizar">
    </form>

    <%
        String msg = (String) request.getAttribute("msg");
        String error = (String) request.getAttribute("error");
        if (msg != null) {
    %>
        <p style="color: green;"><%= msg %></p>
    <% } else if (error != null) { %>
        <p style="color: red;"><%= error %></p>
    <% } %>
</div>

</body>
</html>
