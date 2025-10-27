<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Payment" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>CRUD — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/read.css">
</head>

<body>
<%
    Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("login") : null;
    if (isLogged == null || !isLogged) {
        response.sendRedirect(request.getContextPath() + "/html/login.jsp");
        return;
    }
%>
<div id="background-img">
    <header class="blur">
        <a href="#background-img" class="branding">
            <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN.png" alt="TIMELEAN">
        </a>
        <div>
            <nav class="inter navbar">
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/about" target="_blank">Quem somos</a>
                <a href="${pageContext.request.contextPath}/faq" target="_blank">FAQ</a>
            </nav>
            <a href="${pageContext.request.contextPath}/contact" target="_blank" class="button contact inter">Entrar em contato</a>
        </div>
    </header>

    <div>
        <h1 class="inter-bold">Olá, Administrador.</h1>
        <h3 class="inter-medium">Bem-vindo ao CRUD.</h3>
    </div>
</div>

<main>
    <aside class="inter-medium">
        <img class="branding" src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
        <ul>
            <li><a href="${pageContext.request.contextPath}/payments">Pagamentos</a></li>
            <li><a href="${pageContext.request.contextPath}/plans">Planos</a></li>
            <li><a href="${pageContext.request.contextPath}/plans-insert">Inserções de planos</a></li>
            <li><a href="${pageContext.request.contextPath}/companies">Empresas</a></li>
            <li><a href="${pageContext.request.contextPath}/plants">Plantas industriais</a></li>
            <li><a href="${pageContext.request.contextPath}/emails">Emails de contato</a></li>
        </ul>
    </aside>

    <section>
        <div class="block"></div>
        <h1 class="inter-bold">Pagamentos Cadastrados</h1>

        <div id="filter-bar" class="inter">
            <form action="${pageContext.request.contextPath}/payment/read" method="get">
                <select class="inter" id="status" name="status">
                    <option value="all">Todos</option>
                    <option value="pending">Pendentes</option>
                    <option value="paid">Pagos</option>
                    <option value="canceled">Cancelados</option>
                </select>
                <input type="number" placeholder="Digite o id do plano" name="idPlanSubscription">
                <button class="inter" type="submit">Filtrar</button>

                <input type="text">
            </form>

            <a href="${pageContext.request.contextPath}/html/crud/payment/create.jsp">
                <button class="inter" type="submit">Cadastrar +</button>
            </a>
        </div>

        <table>
            <thead class="inter-hard">
            <tr>
                <th>ID</th>
                <th>Valor (R$)</th>
                <th>Data Limite</th>
                <th>Método</th>
                <th>Beneficiário</th>
                <th>Status</th>
                <th>ID Plano Sub.</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody class="inter-thin">
            <%
                List<Payment> payments = (List<Payment>) request.getAttribute("payments");
                if (payments != null && !payments.isEmpty()) {
                    for (Payment p : payments) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= String.format("%.2f", p.getValue()) %></td>
                <td><%= p.getDeadline() %></td>
                <td><%= p.getMethod() %></td>
                <td><%= p.getBeneficiary() %></td>
                <td><%= p.getStatus() %></td>
                <td><%= p.getIdPlanSubscription() %></td>
                <td>
                    <div class="actions">
                        <form action="${pageContext.request.contextPath}/payment/show" method="get">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <button type="submit" class="see">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/eye.png" alt="Mostrar">
                            </button>
                        </form>
                        <form class="create" action="${pageContext.request.contextPath}/payment/render-update" method="get">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <button type="submit">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/pencil (black).png" alt="Editar">
                            </button>
                        </form>
                        <form class="delete" action="${pageContext.request.contextPath}/payment/delete" method="get">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <button type="submit">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/trash (black).png" alt="Deletar">
                            </button>
                        </form>
                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="8">Nenhum pagamento encontrado.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>
</main>

<footer class="inter-thin">
    <a>
        <button class="branding" id="hivemind">
            <img src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
        </button>
    </a>
    <nav class="inter navbar">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about" target="_blank">Quem somos</a>
        <a href="${pageContext.request.contextPath}/faq" target="_blank">FAQ</a>
    </nav>
    <nav class="medias">
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/whatsapp.png" alt="whatsapp" class="icon"></a>
        <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/instagram.png" alt="instagram" class="icon"></a>
    </nav>
    <p class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
</footer>
</body>
</html>
