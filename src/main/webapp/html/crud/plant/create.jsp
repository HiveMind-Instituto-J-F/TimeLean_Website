<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="hivemind.hivemindweb.models.Plant" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Criar Planta — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
</head>

<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }
    String errorMsg = (String) request.getAttribute("errorMessage");
    String msg = (String) request.getAttribute("msg");
%>

<div class="form">
    <div>
        <div class="block"></div>
        <h1 class="inter-bold">Adicionar Planta</h1>
    </div>

    <form class="inter-thin" action="${pageContext.request.contextPath}/plant/create" method="post">
        <div>
            <h3 class="inter-medium">Informações da Planta</h3>

            <div class="input-div">
                <label for="cnpj">CNPJ</label>
                <input type="text" id="cnpj" name="cnpj" required
                pattern="^[0-9]{14}$">
            </div>

            <div class="input-div">
                <label for="cnae">CNAE</label>
                <input type="text" id="cnae" name="cnae" required
                pattern="^[0-9]{7}$">
            </div>

            <div class="input-div">
                <label for="responsible_cpf">CPF do responsável</label>
                <input type="text" id="responsible_cpf" name="responsible_cpf" required
                pattern="^[0-9]{11}$">
            </div>

            <div class="input-div">
                <label for="company_cnpj">CNPJ da empresa proprietária</label>
                <input type="text" id="company_cnpj" name="company_cnpj" required
                pattern="^[0-9]{14}$">
            </div>

            <div class="input-div">
                <label for="address_number">Endereço</label>
                <input type="text" id="address_number" name="address_number" required>
            </div>

            <div class="input-div">
                <label for="address_cep">CEP</label>
                <input type="text" id="address_cep" name="address_cep" required>
            </div>

            <div class="input-div">
                <label for="operational_status">Status operacional</label>
                <select id="operational_status" name="operational_status">
                    <option value="active">Ativa</option>
                    <option value="inactive">Inativa</option>
                </select>
            </div>
        </div>

        <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
        <input class="button submit inter" type="submit" value="Criar Planta">
    </form>
</div>

</body>
</html>
