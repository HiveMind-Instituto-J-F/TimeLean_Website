<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="hivemind.hivemindweb.models.Plant" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Modify Plant</title>
</head>
<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<header>
    <h1>Modify Industrial Plant</h1>
</header>

<main>
    <%
        Plant plant = (Plant) request.getAttribute("plant");
        if (plant != null) {
    %>
    <form action="${pageContext.request.contextPath}/plant/update" method="post">
        <label for="cnpj">CNPJ:</label>
        <input type="text" id="cnpj" name="CNPJ" value="<%= plant.getCNPJ() %>" readonly><br>

        <label for="cnae">CNAE:</label>
        <input type="text" id="cnae" name="CNAE" value="<%= plant.getCNAE() %>"><br>

        <label for="responsibleCpf">Responsible CPF:</label>
        <input type="text" id="responsibleCpf" name="RESPONSIBLE_CPF" value="<%= plant.getResponsibleCpf() %>"><br>

        <label for="status">Operational Status:</label>
        <select id="status" name="OPERATIONAL_STATUS">
            <option value="true" <%= plant.getOperationalStatus() ? "selected" : "" %>>Active</option>
            <option value="false" <%= !plant.getOperationalStatus() ? "selected" : "" %>>Inactive</option>
        </select><br>

        <label for="cep">Address CEP:</label>
        <input type="text" id="cep" name="ADDRESS_CEP" value="<%= plant.getAdressCep() %>"><br>

        <label for="number">Address Number:</label>
        <input type="number" id="number" name="ADDRESS_NUMBER" value="<%= plant.getAdressNumber() %>"><br>

        <label for="companyCnpj">Company CNPJ:</label>
        <input type="text" id="companyCnpj" name="CNPJ_COMPANY" value="<%= plant.getCnpjCompany() %>" readonly><br>

        <input type="submit" value="Save Changes">
    </form>
    <%
    } else {
    %>
    <p>Plant not found.</p>
    <%
        }
    %>
</main>
</body>
</html>