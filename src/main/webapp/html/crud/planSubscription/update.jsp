<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Atualizar PlanSubscription</title>
</head>
<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<h2>Atualizar PlanSubscription</h2>

<form action="${pageContext.request.contextPath}/plan_subscription/update" method="post">
    <input type="hidden" name="id" value="${planSubscription.id}">

    <label for="start_date">Data de Início:</label><br>
    <input type="date" id="start_date" name="start_date" required value="${planSubscription.startDate}"><br><br>

    <label for="cnpj_company">CNPJ da Empresa:</label><br>
    <input type="text" id="cnpj_company" name="cnpj_company" required placeholder="00.000.000/0000-00" value="${planSubscription.cnpjCompany}" readonly><br><br>

    <label for="id_plan">ID do Plano:</label><br>
    <input type="number" id="id_plan" name="id_plan" required min="1" value="${planSubscription.idPlan}" readonly><br><br>

    <label for="number_installments">Número de Parcelas:</label><br>
    <input type="number" id="number_installments" name="number_installments" required min="1" value="${planSubscription.numberInstallments}" readonly><br><br>
    
    <label for="status">Status:</label><br>
    <select id="status" name="status" required>
        <option value="true" ${planSubscription.status ? 'selected' : ''}>Ativo</option>
        <option value="false" ${!planSubscription.status ? 'selected' : ''}>Inativo</option>
    </select><br><br>


    <button type="submit">Atualizar</button>
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
</body>
</html>
