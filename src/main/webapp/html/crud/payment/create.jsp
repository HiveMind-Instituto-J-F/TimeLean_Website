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
            <h1 class="inter-bold">Adicionar Pagamento</h1>
        </div>

        <form class="inter-thin" action="${pageContext.request.contextPath}/payment/create" method="post">
            <div>
                <h3 class="inter-medium">Informações do pagamento</h3>

                <div class="input-div">
                    <label for="id_plan_sub">ID, Inscrição de plano</label>
                    <input type="text" id="id_plan_sub" name="company-registrant-cpf" placeholder="Insira o ID de inscrição do plano aqui" min="1" required>
                </div>

                <div class="input-div">
                    <label for="beneficiary">Beneficiário</label>
                    <input type="text" name="beneficiary" placeholder="Insira o nome completo do beneficiário aqui" required>
                </div>

                <div class="input-div">
                    <label for="deadline">Vencimento</label>
                    <input class="always-orange" type="date" name="deadline" required>
                </div>

                <div class="input-div">
                    <label for="method">Método de pagamento</label>
                    <select name="method" required>
                        <option value="">Selecione...</option>
                        <option value="PIX">PIX</option>
                        <option value="Cartão de Crédito">Cartão de Crédito</option>
                        <option value="Cartão de Débito">Cartão de Débito</option>
                        <option value="Boleto">Boleto</option>
                        <option value="Transferência">Transferência</option>
                    </select>
                </div>
            </div>
            <p><%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %></p>
            <input class="button submit inter" type="submit" value="Adicionar pagamento">
        </form>
    </div>
</body>

</html>