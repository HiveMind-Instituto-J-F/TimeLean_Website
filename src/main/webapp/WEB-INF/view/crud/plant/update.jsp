<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="hivemind.hivemindweb.models.Plant" %>

<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    Plant plant = (Plant) request.getAttribute("plant");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Modificar Planta — TIMELEAN</title>
</head>

<body>

<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Modificar Planta</h1>
    </div>

    <% if (plant != null) { %>
        <form class="inter-thin" action="${pageContext.request.contextPath}/plant/update" method="post">

            <div>
                <h3 class="inter-medium">Informações da planta</h3>

                <div class="input-div">
                    <label for="cnpj">CNPJ</label>
                    <input type="text" id="cnpj" name="CNPJ"
                           value="<%= (plant.getCnpj() != null) ? plant.getCnpj() : "" %>" readonly>
                </div>

                <div class="input-div">
                    <label for="cnae">CNAE</label>
                    <input type="text" id="cnae" name="CNAE"
                           value="<%= (plant.getCnae() != null) ? plant.getCnae() : "" %>"
                           required pattern="^[0-9]{7}$">
                </div>

                <div class="input-div">
                    <label for="responsibleCpf">CPF do responsável</label>
                    <input type="text" id="responsibleCpf" name="RESPONSIBLE_CPF"
                           value="<%= (plant.getResponsibleCpf() != null) ? plant.getResponsibleCpf() : "" %>"
                           required pattern="^[0-9]{11}$">
                </div>

                <div class="input-div">
                    <label for="companyCnpj">CNPJ da empresa proprietária</label>
                    <input type="text" id="companyCnpj" name="CNPJ_COMPANY"
                           value="<%= (plant.getCnpjCompany() != null) ? plant.getCnpjCompany() : "" %>" readonly>
                </div>

                <div class="input-div">
                    <label for="number">Número do endereço</label>
                    <input type="number" id="number" name="ADDRESS_NUMBER"
                           value="<%=plant.getAddressNumber()%>">
                </div>

                <div class="input-div">
                    <label for="cep">CEP</label>
                    <input type="text" id="cep" name="ADDRESS_CEP"
                           value="<%= (plant.getAddressCep() != null) ? plant.getAddressCep() : "" %>">
                </div>
            </div>

            <input class="button submit inter" type="submit" value="Salvar Alterações">
        </form>

    <% } else { %>
        <p class="inter-medium" style="color:red;">Planta não encontrada.</p>
    <% } %>
</div>

</body>
</html>
