<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.PlanSubscription" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Lista de Empresas — TIMELEAN</title>

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
        <a href="#" class="branding">
            <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN (black).png" alt="TIMELEAN">
        </a>

        <div id="redirects">
            <nav class="inter navbar">
                <a href="${pageContext.request.contextPath}/index.html">Home</a>
                <a href="${pageContext.request.contextPath}/html/aboutUs.html">Quem somos</a>
                <a href="${pageContext.request.contextPath}/html/crud/worker/login/login.jsp">Gestão de trabalhadores</a>
            </nav>
        </div>
    </header>

    <div>
        <h1 class="inter-bold">Olá, Administrador.</h1>
        <h3 class="inter-medium">Bem vindo ao CRUD.</h3>
    </div>
</div>

<main>
    <!-- Barra lateral ↓ -->
    <aside id="crud-sidebar" class="sidebar inter-thin">
        <div id="sidebar-top">
            <img class="branding" src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
            <label for="hamburguer"><img src="${pageContext.request.contextPath}/img/icons/ui/hamburguer (white).png" alt="Menu"></label>
            <input name="hamburguer" id="hamburguer" type="checkbox" checked>
        </div>

        <div id="summary">
            <details>
                <summary class="topic-title">
                    <h3 class="inter-hard">CRUD</h3>
                    <i class="seta fa-solid fa-caret-right"></i>
                </summary>

                <ul>
                    <li><a href="${pageContext.request.contextPath}/company/read">Empresas</a></li>
                    <li><a href="${pageContext.request.contextPath}/payment/read">Pagamentos</a></li>
                    <li><a href="${pageContext.request.contextPath}/plan/read">Planos</a></li>
                    <li><a href="${pageContext.request.contextPath}/plan_subscription/read">Inserções de planos</a></li>
                    <li><a href="${pageContext.request.contextPath}/plant/read">Plantas industriais</a></li>
                </ul>
            </details>

            <a href="${pageContext.request.contextPath}/index.html">
                <h3>Home</h3>
            </a>
            <a href="${pageContext.request.contextPath}/html/aboutUs.html">
                    <h3>Sobre nós</h3>
                </a>
            <a href="${pageContext.request.contextPath}/html/email/send.jsp" target="_blank">
                <h3>Entrar em contato</h3>
            </a>
        </div>
    </aside>

    <!-- CRUD ↓ -->
    <section> <!-- Desabilitar o display -->
        <div class="block"></div>
        <h1 class="inter-bold">Inserções de Planos Cadastradas</h1>

        <!-- Filtro e submit ↓ -->
        <div id="filter-bar" class="inter">
            <form action="${pageContext.request.contextPath}/plan_subscription/read" method="get">
                <div>
                    <label for="cnpj_company">
                        <img src="${pageContext.request.contextPath}/img/icons/ui/search.png" alt="Buscar">
                    </label>
                    <input type="text" name="cnpj_company" placeholder="Digite o nome da empresa dona aqui">
                </div>
                <div>
                    <label for="id_plan">
                        <img src="${pageContext.request.contextPath}/img/icons/ui/search.png" alt="Buscar">
                    </label>
                    <input type="text" name="id_plan" placeholder="Digite o CNPJ da empresa aqui">
                </div>
                <button class="inter" type="submit">Submeter</button>
            </form>

            <div>
                <a class="inter" href="${pageContext.request.contextPath}/html/crud/planSubscription/create.jsp">Cadastrar
                    nova inserção</a>
            </div>
        </div>

        <!-- Table ↓ -->
        <div id="wrap">
            <table>
                <!-- Theader ↓ -->
                <thead class="inter-hard">
                <tr>
                    <th>ID</th>
                    <th>Data Início</th>
                    <th>CNPJ Empresa</th>
                    <th>ID Plano</th>
                    <th>Nº Parcelas</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
                </thead>

                <!-- Estrutura principal ↓ -->
                <tbody class="inter-thin">
                <%
                    List<PlanSubscription> planSubs = (List<PlanSubscription>) request.getAttribute("planSubs");
                    if (planSubs != null && !planSubs.isEmpty()) {
                        for (PlanSubscription ps : planSubs) {
                            if (ps.getStatus()) {
                %>
                <tr>
                    <td><%= ps.getId() %></td>
                    <td class="sensitive" data-real="<%= ps.getStartDate() %>"><%= "*".repeat(ps.getStartDate().toString().length()) %></td>
                    <td class="sensitive" data-real="<%= ps.getCnpjCompany() %>"><%= "*".repeat(ps.getCnpjCompany().length()) %></td>
                    <td><%= ps.getIdPlan() %></td>
                    <td><%= ps.getNumberInstallments() %></td>
                    <td><%= ps.getStatus() ? "Ativo" : "Inativo" %></td>
                    <td>
                        <div class="actions">
                            <form action="show" method="get">
                                <button type="button" class="see toggle-visibility">
                                    <img class="eye open" src="${pageContext.request.contextPath}/img/icons/ui/eye (open).png"
                                         alt="Mostrar">
                                    <img class="eye closed" src="${pageContext.request.contextPath}/img/icons/ui/eye (closed).png"
                                         alt="Ocultar">
                                </button>
                            </form>

                            <form class="create" action="${pageContext.request.contextPath}/plan_subscription/render-update" method="get">
                                <input type="hidden" name="id" value="<%= ps.getId() %>">
                                <button type="submit">
                                    <img src="${pageContext.request.contextPath}/img/icons/ui/pencil (black).png" alt="Editar">
                                </button>
                            </form>

                            <form class="delete" action="${pageContext.request.contextPath}/plan_subscription/delete" method="get">
                                <button type="submit">
                                    <input type="hidden" name="id" value="<%= ps.getId() %>">
                                    <img src="${pageContext.request.contextPath}/img/icons/ui/trash (black).png" alt="Deletar">
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td><%= ps.getId() %></td>
                    <td class="sensitive" data-real="<%= ps.getStartDate() %>"><%= "*".repeat(ps.getStartDate().toString().length()) %></td>
                    <td class="sensitive" data-real="<%= ps.getCnpjCompany() %>"><%= "*".repeat(ps.getCnpjCompany().length()) %></td>
                    <td><%= ps.getIdPlan() %></td>
                    <td><%= ps.getNumberInstallments() %></td>
                    <td><%= ps.getStatus() ? "Ativo" : "Inativo" %></td>
                    <td>
                        <form class="disable" action="${pageContext.request.contextPath}/plan_subscription/delete/rollback" method="post">
                            <input type="hidden" name="id" value="<%= ps.getId() %>">
                            <input type="submit" value="Reativar"/>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                } else {
                %>
                <tr>
                    <td colspan="8">Nenhuma inscrição de plano foi encontrada.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>

<footer class="inter-thin">
    <a style="cursor: none;">
        <button class="branding" id="hivemind">
            <img src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
        </button>
    </a>
    <nav id="redirects" class="inter navbar">
        <a href="${pageContext.request.contextPath}/html/aboutUs.html">Quem somos</a>
        <a href="${pageContext.request.contextPath}/index.html" target="">Home</a>
        <a href="${pageContext.request.contextPath}/html/crud/worker/login/login.jsp">Gestão de trabalhadores</a>
    </nav>
    <nav class="medias">
        <a href="https://github.com/HiveMind-Instituto-J-F/TimeLean_Website"><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
    </nav>
    <p id="copy-right" class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
</footer>
</body>

<script src="${pageContext.request.contextPath}/js/dataReal.js"></script>

</html>
