<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>

<%
Company company = (Company) request.getAttribute("company");
%>

<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TIMELEAN</title>
    </head>
    <body>
    <%
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
        if (isLogged == null || !isLogged) {
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");
            return;
        }
    %>
        <h1>Relação COMPANY</h1>
        <h2>UPDATE COMPANY</h2>
        <form action="${pageContext.request.contextPath}/company/update" method="post">
            <label for="cnpj">CNPJ:</label>
            <input type="text" id="cnpj" name="cnpj" value="<%= company.getCNPJ() %>" required readonly><br><br>

            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" value="<%= company.getName() %>" required><br><br>

            <label for="cnae">CNAE:</label>
            <input type="text" id="cnae" name="cnae" value="<%= company.getCnae() %>" required><br><br>

            <label for="registrantCpf">CPF do Registrante:</label>
            <input type="text" id="registrantCpf" name="registrantCpf" value="<%= company.getRegistrantCpf() %>" required readonly><br><br>

            <input type="submit" value="Atualizar">
        </form>
    </body>
</html>
