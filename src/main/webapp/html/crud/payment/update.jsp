<%@ page import="hivemind.hivemindweb.models.Payment" %>
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
        <title>Atualizar Pagamento — TIMELEAN</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/form.css">
    </head>

    <body>
        <%
            // Validação de login
            Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
            if (isLogged == null || !isLogged) {
                response.sendRedirect(request.getContextPath() + "/html/login.jsp");
                return;
            }

            // Validação de ID
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("list.jsp");
                return;
            }

            // Recupera o pagamento vindo do servlet
            Payment payment = (Payment) request.getAttribute("payment");

            // Mensagens de retorno
            String msg = (String) request.getAttribute("msg");
            String errorMsg = (String) request.getAttribute("errorMsg");
        %>

        <div class="form">
            <div>
                <div class="block"></div>
                <h1 class="inter-bold">Atualizar Pagamento</h1>
            </div>

            <form class="inter-thin" action="${pageContext.request.contextPath}/payment/update" method="post">
                <!-- ID oculto -->
                <input type="hidden" name="id" value="<%=payment.getId()%>">

                <div>
                    <h3 class="inter-medium">Informações do pagamento</h3>

                    <div class="input-div">
                        <label for="beneficiary">Beneficiário</label>
                        <input type="text" id="beneficiary" name="beneficiary" value="<%=payment.getBeneficiary()%>" required>
                    </div>

                    <div class="input-div">
                        <label for="deadline">Vencimento</label>
                        <input class="always-orange" type="date" id="deadline" name="deadline" value="<%=payment.getDeadline()%>" required>
                    </div>

                    <div class="input-div">
                        <label for="method">Método de pagamento</label>
                        <select id="method" name="method" required>
                            <option value="">Selecione...</option>
                            <option value="PIX" <%= "PIX".equals(payment.getMethod()) ? "selected" : "" %>>PIX</option>
                            <option value="Cartão de Crédito" <%= "Cartão de Crédito".equals(payment.getMethod()) ? "selected" : "" %>>Cartão de Crédito</option>
                            <option value="Cartão de Débito" <%= "Cartão de Débito".equals(payment.getMethod()) ? "selected" : "" %>>Cartão de Débito</option>
                            <option value="Boleto" <%= "Boleto".equals(payment.getMethod()) ? "selected" : "" %>>Boleto</option>
                            <option value="Transferência" <%= "Transferência".equals(payment.getMethod()) ? "selected" : "" %>>Transferência</option>
                        </select>
                    </div>

                    <div class="input-div">
                        <label for="status">Status do pagamento</label>
                        <select id="status" name="status" required>
                            <option value="">Selecione...</option>
                            <option value="Pendente" <%= "Pendente".equals(payment.getStatus()) ? "selected" : "" %>>Pendente</option>
                            <option value="Pago" <%= "Pago".equals(payment.getStatus()) ? "selected" : "" %>>Pago</option>
                            <option value="Atrasado" <%= "Atrasado".equals(payment.getStatus()) ? "selected" : "" %>>Atrasado</option>
                            <option value="Cancelado" <%= "Cancelado".equals(payment.getStatus()) ? "selected" : "" %>>Cancelado</option>
                        </select>
                    </div>
                </div>

                <!-- Mensagem de sucesso ou erro -->
                <p class="form-msg">
                    <%= (errorMsg != null) ? "Erro: " + errorMsg : (msg != null ? msg : "") %>
                </p>

                <!-- Botão de envio -->
                <input class="button submit inter" type="submit" value="Atualizar Pagamento">
            </form>
        </div>
    </body>
</html>
