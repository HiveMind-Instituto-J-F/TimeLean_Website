<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>

<%
Company company = (Company) request.getAttribute("company");
%>

<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
        <title>Atualizar — TIMELEAN</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
    </head>
    <body>
        <%
            Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
            if (isLogged == null || !isLogged) {
                response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
                return;
            }
        %>
        <div class="form">
            <div>
                <div class="block"></div>
                <h1 class="inter-bold">Atualizar Informações</h1>
            </div>
        
            <form class="inter-thin" action="${pageContext.request.contextPath}/company/update" method="post">
                <div>
                    <h3 class="inter-medium">Informações da empresa</h3>
                    
                    <div class="input-div">
                        <label for="name">Nome da empresa</label>
                        <input class="always-orange" type="text" id="name" name="name" value="<%= company.getName() %>" required>
                    </div>
                    <div class="input-div">
                        <label for="cnpj">CNPJ da empresa</label>
                        <input type="text" id="cnpj" name="cnpj" value="<%= company.getCnpj() %>" readonly>
                    </div>
                    <div class="input-div">
                        <label for="cnae">CNAE</label>
                        <input type="text" id="cnae" name="cnae" value="<%= company.getCnae() %>" required
                        pattern="^[0-9]{7}$">
                    </div>
                    <div class="input-div">
                        <label for="registrantCpf">CPF do registrante</label>
                        <input type="text" id="registrantCpf" name="registrantCpf" value="<%= company.getRegistrantCpf() %>" readonly>
                    </div>
                </div>
                <input class="button submit inter" type="submit" value="Atualizar">
            </form>
        </div>
    </body>
</html>
