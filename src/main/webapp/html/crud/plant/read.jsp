<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Plant" %>

<html>
<head>
    <title>Lista de Empresas</title>
</head>
<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }
%>
<h2>Plantas industriais Cadastradas</h2>
<a href="${pageContext.request.contextPath}/html/crud/plant/create.jsp">CRIARR</a>
<form action="${pageContext.request.contextPath}/plant/read" method="get">
    <select id="filter" name="filter">
        <option value="active-plants">Apenas plantas ativas</option>
        <option value="all-plants">Todas plantas</option>
        <option value="inactive-plants">Apenas plantas inativas</option>
    </select>
    <input type="text" name="filterCompanyName" placeholder="Filtrar por nome de empresa dona">

    <button type="submit">Filtrar</button>
</form>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>CNPJ</th>
        <th>CNAE</th>
        <th>Responsible CPF</th>
        <th>Operational Status</th>
        <th>Address Number</th>
        <th>Address CEP</th>
        <th>CNPJ Company</th>
    </tr>

    <%
        List<Plant> plantList = (List<Plant>) request.getAttribute("plantList");
        if (plantList != null && !plantList.isEmpty()) {
            for (Plant p : plantList) {
                boolean active = p.getOperationalStatus();
    %>
    <tr style="color:<%= active ? "black" : "red" %>;">
        <td><%= p.getCNPJ() %></td>
        <td><%= p.getCNAE() %></td>
        <td><%= p.getResponsibleCpf() %></td>
        <td><%= p.getOperationalStatus() %></td>
        <td><%= p.getAdressNumber() %></td>
        <td><%= p.getAdressCep() %></td>
        <td><%= p.getCnpjCompany() %></td>
        <td>
            <% if (active) { %>
            <form action="${pageContext.request.contextPath}/plant/delete" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= p.getCNPJ() %>"/>
                <input type="submit" value="Delete"/>
            </form>
            <form action="${pageContext.request.contextPath}/plant/render-update" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= p.getCNPJ() %>"/>
                <input type="submit" value="Modify"/>
            </form>
            <% } else { %>
            <form action="${pageContext.request.contextPath}/plant/delete/rollback" method="post" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= p.getCNPJ() %>"/>
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
        <td colspan="8">Nenhuma planta industrial encontrada.</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
