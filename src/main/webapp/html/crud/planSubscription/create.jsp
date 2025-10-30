<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Criar Assinatura de Plano — TIMELEAN</title>

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
        <h1 class="inter-bold">Criar Assinatura de Plano</h1>
    </div>

    <form class="inter-thin" action="${pageContext.request.contextPath}/plan_subscription/create" method="post">
        <div>
            <h3 class="inter-medium">Informações da assinatura</h3>

            <div class="input-div">
                <label for="id_plan">ID do plano</label>
                <input type="number" id="id_plan" name="id_plan" placeholder="Insira o ID do plano aqui" required>
            </div>

            <div class="input-div">
                <label for="cnpj_company">CNPJ da empresa</label>
                <input type="text" id="cnpj_company" name="cnpj_company" placeholder="Ex: 60.960.145/0001-66" required
                pattern="^[0-9]{14}$">
            </div>

            <div class="input-div">
                <label for="start_date">Data de início</label>
                <input type="date" id="start_date" name="start_date" required>
            </div>

            <div class="input-div">
                <label for="number_installments">Quantidade de parcelas</label>
                <input type="number" id="number_installments" name="number_installments" placeholder="Insira a quantidade de parcelas aqui" required>
            </div>

            <div class="input-div">
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="true">Ativo</option>
                    <option value="false">Inativo</option>
                </select>
            </div>
        </div>

        <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
        <input class="button submit inter" type="submit" value="Criar">
    </form>
</div>

</body>
</html>
