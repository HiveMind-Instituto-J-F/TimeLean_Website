<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/text.css">
    <link rel="stylesheet" href="../css/others/login.css">

    <title>TIMELEAN</title>
    <link rel="shortcut icon" href="../img/favicon/home-v2.png" type="image/x-icon">
</head>
<body>
<h1>Relacao WORKER</h1>
<h2>Workers List</h2>
<a href="<%= request.getContextPath() %>/html/crud/worker/create.jsp">Add worker</a>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>CPF</th>
        <th>Role</th>
        <th>Sector</th>
        <th>Name</th>
        <th>Login Email</th>
        <th>CNPJ Plant</th>
        <th>Actions</th>
    </tr>

    <%
    String responsibleCpf = (String) request.getSession().getAttribute("responsibleCpf");
        
        List<Worker> workers = (List<Worker>) request.getAttribute("workers");
        if (workers != null) {
            for (Worker w : workers) {
                if (responsibleCpf != null && !responsibleCpf.equals(w.getCpf())) { 
    %>
    <tr>
        <td><%= w.getCpf() %></td>
        <td><%= w.getRole() %></td>
        <td><%= w.getSector() %></td>
        <td><%= w.getName() %></td>
        <td><%= w.getLoginEmail() %></td>
        <td><%= w.getCnpjPlant() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/delete" method="post" style="display:inline;">
                <input type="hidden" name="cpf" value="<%= w.getCpf() %>"/>
                <input type="submit" value="Delete"/>
            </form>
            <form action="${pageContext.request.contextPath}/render-update" method="post" style="display:inline;">
                <input type="hidden" name="cpf" value="<%= w.getCpf() %>"/>
                <input type="submit" value="Modify"/>
            </form>
        </td>
    </tr>
    <%// PAREI AQUI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                } else if (responsibleCpf != null && responsibleCpf.equals(w.getCpf())){
                    
    %>
    <tr>
        <td><%= w.getCpf() %></td>
        <td><%= w.getRole() %></td>
        <td><%= w.getSector() %></td>
        <td><%= w.getName() %></td>
        <td><%= w.getLoginEmail() %></td>
        <td><%= w.getCnpjPlant() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/render-update" method="post" style="display:inline;">
                <input type="hidden" name="cpf" value="<%= w.getCpf() %>"/>
                <input type="submit" value="Modify"/>
            </form>
        </td>
    </tr>
    <%
                }
            }
        } else {
    %>
    <tr>
        <td colspan="7">No workers found</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>