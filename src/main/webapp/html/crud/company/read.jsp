<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TIMELEAN</title>
    <link rel="shortcut icon" href="../img/favicon/home-v2.png" type="image/x-icon">
</head>
<body>
<h1>Relacao COMPANY</h1>
<h2>Companies List</h2>
<a href="<%= request.getContextPath() %>/html/crud/company/create.jsp">Add company</a>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>CNPJ</th>
        <th>Name</th>
        <th>CNAE</th>
        <th>REGISTRANT_CPF</th>
        <th>Actions</th>
    </tr>

    <%
        List<Company> companies = (List<Company>) request.getAttribute("companies");
        if (companies != null) {
            for (Company c : companies) {
    %>
    <tr>
        <td><%= c.getCNPJ() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.getCnae() %></td>
        <td><%= c.getRegistrantCpf() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/company/delete" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Delete"/>
            </form>
            <form action="${pageContext.request.contextPath}/company/render-update" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Modify"/>
            </form>
        </td>
    </tr>
    <%
            }
    } else {
    %>
    <tr>
        <td colspan="7">No companies found</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
=======
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
        <option value="active-companies">Apenas empresas ativas</option>
        <option value="all-companies">Todas</option>
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
            <form action="${pageContext.request.contextPath}/company/delete/rollback" method="post" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Reativar"/>
            </form>
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
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
