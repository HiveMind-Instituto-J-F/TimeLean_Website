<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Criar PlanSubscription</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        label { display: block; margin-top: 10px; }
        input { padding: 5px; width: 250px; }
        button { margin-top: 15px; padding: 8px 15px; }
        .msg-success { color: green; }
        .msg-error { color: red; }
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

<h2>Criar Nova PlanSubscription</h2>

<form action="${pageContext.request.contextPath}/plan_subscription/create" method="post">
    <label for="start_date">Data de Início:</label>
    <input type="date" id="start_date" name="start_date" required>

    <label for="cnpj_company">CNPJ da Empresa:</label>
    <input type="text" id="cnpj_company" name="cnpj_company" required
           placeholder="00.000.000/0000-00">

    <input type="number" placeholder="quantidade_parcelas" name="number_installments">

    <label for="id_plan">ID do Plano:</label>
    <input type="number" id="id_plan" name="id_plan" required min="1">

    <label for="status">Status:</label><br>
    <select id="status" name="status" required>
        <option value="true" ${planSubscription.status ? 'selected' : ''}>Ativo</option>
        <option value="false" ${!planSubscription.status ? 'selected' : ''}>Inativo</option>
    </select><br><br>



    <button type="submit">Enviar</button>
</form>

<%
    String msg = (String) request.getAttribute("msg");
    String error = (String) request.getAttribute("error");
    if (msg != null) {
%>
    <p class="msg-success"><%= msg %></p>
<% } else if (error != null) { %>
    <p class="msg-error"><%= error %></p>
<% } %>

</body>
</html>
