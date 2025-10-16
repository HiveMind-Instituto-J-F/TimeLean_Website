<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>

<html>
<head>
    <title>Lista de Empresas</title>
</head>
<body>
<h2>Empresas Cadastradas</h2>

<form action="${pageContext.request.contextPath}/company/read" method="get">
    <select id="status" name="status">
        <option value="all-companies">Todas</option>
        <option value="active-companies">Apenas empresas ativas</option>
        <option value="inactive-companies">Apenas empresas inativas</option>
        <option value="companies-with-pending-payments">Apenas empresas com pagamentos pendentes</option>
    </select>
    <button type="submit">Filtrar</button>
</form>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>CNPJ</th>
        <th>Nome</th>
        <th>CNAE</th>
        <th>Registrant CPF</th>
        <th>Ações</th>
    </tr>

    <%
        List<Company> companies = (List<Company>) request.getAttribute("companies");
        if (companies != null && !companies.isEmpty()) {
            for (Company c : companies) {
                boolean active = c.isActive();
    %>
    <tr style="color:<%= active ? "black" : "red" %>;">
        <td><%= c.getCNPJ() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.getCnae() %></td>
        <td><%= c.getRegistrantCpf() %></td>
        <td>
            <% if (active) { %>
            <form action="${pageContext.request.contextPath}/company/delete" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Delete"/>
            </form>
            <form action="${pageContext.request.contextPath}/company/render-update" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Modify"/>
            </form>
            <% } else { %>
            Empresa desativada
            <% } %>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">Nenhuma empresa encontrada.</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
