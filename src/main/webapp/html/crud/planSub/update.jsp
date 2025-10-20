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

    // Recebendo parâmetros existentes para preencher o formulário (opcional)
    String id = request.getParameter("id");
    String startDate = request.getParameter("start_date");
    String cnpjCompany = request.getParameter("cnpj_company");
    String idPlan = request.getParameter("id_plan");
%>

<h2>Atualizar PlanSubscription</h2>

<form action="/HivemindWeb_war/udpate-paymount" method="post">
    <input type="hidden" name="id" value="<%= id != null ? id : "" %>">

    <label for="start_date">Data de Início:</label><br>
    <input type="date" id="start_date" name="deadline" required value="<%= startDate != null ? startDate : "" %>"><br><br>

    <label for="cnpj_company">CNPJ da Empresa:</label><br>
    <input type="text" id="cnpj_company" name="beneficiary" required placeholder="00.000.000/0000-00" value="<%= cnpjCompany != null ? cnpjCompany : "" %>"><br><br>

    <label for="id_plan">ID do Plano:</label><br>
    <input type="number" id="id_plan" name="id_plan_sub" required min="1" value="<%= idPlan != null ? idPlan : "" %>"><br><br>

    <label for="method">Método de Pagamento:</label><br>
    <input type="text" id="method" name="method" required placeholder="Ex: Cartão, Boleto"><br><br>

    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status" required placeholder="Ex: Pendente, Pago"><br><br>

    <label for="number_installments">Número de Parcelas:</label><br>
    <input type="number" id="number_installments" name="number_installments" required min="1"><br><br>

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
