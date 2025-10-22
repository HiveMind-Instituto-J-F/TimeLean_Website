<%@ page import="hivemind.hivemindweb.models.Payment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Pagamento</title>

</head>
<body>
<%
//    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
//    if (isLogged == null || !isLogged) {
//        response.sendRedirect("../login.jsp");
//        return;
//    }

    // Captura o ID do pagamento da URL
    String idParam = request.getParameter("id");
    if (idParam == null || idParam.isEmpty()) {
        response.sendRedirect("list.jsp");
        return;
    }
    Payment payment = (Payment) request.getAttribute("payment");
%>

<div class="container">
    <h1>Editar Pagamento</h1>

    <form action="${pageContext.request.contextPath}/payment/update" method="post">
        <input type="hidden" name="id" value="<%= idParam %>">

        <div class="form-group">
            <label>Vencimento <span class="required">*</span></label>
            <input type="date" name="deadline" value="<%=payment.getDeadline()%>" required>
        </div>

        <div class="form-group">
            <label>Método <span class="required">*</span></label>
            <select name="method" required>
                <option value="">Selecione...</option>
                <option value="PIX" <%= "PIX".equals(payment.getStatus()) ? "selected" : "" %>>PIX</option>
                <option value="Cartão de Crédito" <%= "Cartão de Crédito".equals(payment.getStatus()) ? "selected" : "" %>>Cartão de Crédito</option>
                <option value="Cartão de Débito" <%= "Cartão de Débito".equals(payment.getStatus()) ? "selected" : "" %>>Cartão de Débito</option>
                <option value="Boleto" <%= "Boleto".equals(payment.getStatus()) ? "selected" : "" %>>Boleto</option>
                <option value="Transferência" <%= "Transferência".equals(payment.getStatus()) ? "selected" : "" %>>Transferência</option>
            </select>

        </div>

        <div class="form-group">
            <label>Beneficiário <span class="required">*</span></label>
            <input type="text" name="beneficiary" required value="<%=payment.getBeneficiary()%>">
        </div>

        <div class="form-group">
            <label>Status <span class="required">*</span></label>
            <select name="status" required>
                <option value="">Selecione...</option>
                <option value="Pendente" <%= "Pendente".equals(payment.getStatus()) ? "selected" : "" %>>Pendente</option>
                <option value="Pago" <%= "Pago".equals(payment.getStatus()) ? "selected" : "" %>>Pago</option>
                <option value="Atrasado" <%= "Atrasado".equals(payment.getStatus()) ? "selected" : "" %>>Atrasado</option>
                <option value="Cancelado" <%= "Cancelado".equals(payment.getStatus()) ? "selected" : "" %>>Cancelado</option>
            </select>
        </div>


        <button type="submit">Atualizar Pagamento</button>
    </form>

    <% String msg = (String) request.getAttribute("msg"); %>
    <% if (msg != null) { %>
        <div class="message"><%= msg %></div>
    <% } %>
</div>
</body>
</html>
