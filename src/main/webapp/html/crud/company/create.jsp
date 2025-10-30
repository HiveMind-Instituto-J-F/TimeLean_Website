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
    <title>Criar — TIMELEAN</title>

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
            <h1 class="inter-bold">Registrar Empresa</h1>
        </div>

        <form class="inter-thin" action="${pageContext.request.contextPath}/company/create" method="post">
            <div>
                <h3 class="inter-medium">Informações da empresa</h3>

                <div class="input-div">
                    <label for="company-name">Nome da empresa</label>
                    <input class="always-orange" type="text" id="name" name="company-name" required>
                </div>

                <div class="input-div">
                    <label for="company-cnpj">CNPJ da empresa</label>
                    <input type="text" id="cnpj" name="company-cnpj" placeholder="Ex: 60.960.145/0001-66" required
                        pattern="^[0-9]{14}$">
                </div>
                
                <div class="input-div">
                    <label for="company-cnae">CNAE</label>
                    <input type="text" id="cnae" name="company-cnae" placeholder="Ex: 62.01-5/13" required
                        pattern="^[0-9]{7}$">
                </div>
                
                <div class="input-div">
                    <label for="company-registrant-cpf">CPF do registrante</label>
                    <input type="text" id="company-registrantCpf" name="company-registrant-cpf" placeholder="Ex: 979.160.830-23"
                        required pattern="^[0-9]{11}$">
                </div>
            </div>
            <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
            <input class="button submit inter" type="submit" value="Registrar">
        </form>
    </div>
</body>

</html>