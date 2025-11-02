<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Worker" %>

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
    <title>Gestão de Trabalhadores — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud/read.css">
</head>

<body>

<%
    boolean isLogged = session != null && session.getAttribute("plantCnpj") != null;
    if (!isLogged) {
        response.sendRedirect(request.getContextPath() + "/pages/workerLogin.jsp");
        return;
    }

    String responsibleCpf = (String) session.getAttribute("responsibleCpf");
    List<Worker> workers = (List<Worker>) request.getAttribute("workers");
%>
<div id="background-img">
    <header class="blur">
        <a href="#" class="branding">
            <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN%20(black).png" alt="TIMELEAN">
        </a>

        <div id="redirects">
            <nav class="inter navbar">
                <a href="${pageContext.request.contextPath}/index.html">Home</a>
                <a href="${pageContext.request.contextPath}/pages/aboutUs.html" target="_blank">Quem somos</a>
                <a href="${pageContext.request.contextPath}/pages/workerLogin.jsp">Gestão de trabalhadores</a>
            </nav>
        </div>
    </header>

    <div>
        <h1 class="inter-bold">Olá, Administrador.</h1>
        <h3 class="inter-medium">Bem-vindo à Gestão de Trabalhadores.</h3>
    </div>
</div>

<main>
    <!-- Barra lateral -->
    <aside id="crud-sidebar" class="sidebar inter-thin">
        <div id="sidebar-top">
            <img class="branding" src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND%20(white).png" alt="HIVEMIND">
            <label for="hamburguer"><img src="${pageContext.request.contextPath}/img/icons/ui/hamburguer%20(white).png" alt="Menu"></label>
            <input name="hamburguer" id="hamburguer" type="checkbox" checked>
        </div>

        <div id="summary">
            <a href="${pageContext.request.contextPath}/index.html"><h3>Home</h3></a>
            <a href="${pageContext.request.contextPath}/pages/aboutUs.html"><h3>Sobre nós</h3></a>
            <a href="${pageContext.request.contextPath}/pages/email/send.jsp" target="_blank"><h3>Entrar em contato</h3></a>
        </div>
    </aside>

    <!-- CRUD -->
    <section>
        <div class="block"></div>
        <h1 class="inter-bold">Lista de Trabalhadores</h1>

        <!-- Filtro -->
        <div id="filter-bar" class="inter">
            <form action="${pageContext.request.contextPath}/worker/read" method="get">
                <div>
                    <label for="cpfFilter">
                        <img src="${pageContext.request.contextPath}/img/icons/ui/search.png" alt="Buscar">
                    </label>
                    <input type="text" name="cpfFilter" placeholder="Digite o CPF do trabalhador aqui">
                </div>
                <div>
                    <label for="sectorFilter">
                        <img src="${pageContext.request.contextPath}/img/icons/ui/search.png" alt="Buscar">
                    </label>
                    <input type="text" name="sectorFilter" placeholder="Digite o setor do trabalhador aqui">
                </div>
                <button class="inter" type="submit">Submeter</button>
            </form>

            <div>
                <a class="inter" href="${pageContext.request.contextPath}/pages/create/worker.jsp">
                    Cadastrar novo usuário
                </a>
            </div>
        </div>

        <!-- Tabela -->
        <div id="wrap">
            <table>
                <thead class="inter-hard">
                    <tr>
                        <th>Nome</th>
                        <th>E-mail</th>
                        <th>Cargo</th>
                        <th>Setor</th>
                        <th>CPF</th>
                        <th>CNPJ da planta</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <tbody class="inter-thin">
                    <%
                        if (workers != null && !workers.isEmpty()) {
                            for (Worker w : workers) {
                                if (responsibleCpf != null && !responsibleCpf.equals(w.getCpf())) {
                    %>
                    <tr>
                        <td><%= w.getName() %></td>
                        <td class="sensitive" data-real="<%= w.getLoginEmail() %>">
                            <%= "*".repeat(w.getLoginEmail() != null ? w.getLoginEmail().length() : 0) %>
                        </td>
                        <td><%= w.getRole() %></td>
                        <td><%= w.getSector() %></td>
                        <td class="sensitive" data-real="<%= w.getCpf() %>">
                            <%= "*".repeat(w.getCpf() != null ? w.getCpf().length() : 0) %>
                        </td>
                        <td class="sensitive" data-real="<%= w.getCnpjPlant() %>">
                            <%= "*".repeat(w.getCnpjPlant() != null ? w.getCnpjPlant().length() : 0) %>
                        </td>
                        <td>
                            <div class="actions">
                                <form action="show" method="get">
                                    <button type="button" class="see toggle-visibility">
                                        <img class="eye open" src="${pageContext.request.contextPath}/img/icons/ui/eye%20(open).png" alt="Mostrar">
                                        <img class="eye closed" src="${pageContext.request.contextPath}/img/icons/ui/eye%20(closed).png" alt="Ocultar">
                                    </button>
                                </form>

                                <form class="create" action="${pageContext.request.contextPath}/worker/render-update" method="post">
                                    <input type="hidden" name="cpf" value="<%= w.getCpf() %>">
                                    <button type="submit">
                                        <img src="${pageContext.request.contextPath}/img/icons/ui/pencil%20(black).png" alt="Editar">
                                    </button>
                                </form>
                                
                                <form class="delete" action="${pageContext.request.contextPath}/worker/delete" method="post">
                                    <input type="hidden" name="cpf" value="<%= w.getCpf() %>">
                                    <button type="submit">
                                        <img src="${pageContext.request.contextPath}/img/icons/ui/trash%20(black).png" alt="Deletar">
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <%
                                } else if (responsibleCpf != null && responsibleCpf.equals(w.getCpf())) {
                    %>
                    <tr>
                        <td><%= w.getName() %></td>
                        <td class="sensitive" data-real="<%= w.getLoginEmail() %>">
                            <%= "*".repeat(w.getLoginEmail() != null ? w.getLoginEmail().length() : 0) %>
                        </td>
                        <td><%= w.getRole() %></td>
                        <td><%= w.getSector() %></td>
                        <td class="sensitive" data-real="<%= w.getCpf() %>">
                            <%= "*".repeat(w.getCpf() != null ? w.getCpf().length() : 0) %>
                        </td>
                        <td class="sensitive" data-real="<%= w.getCnpjPlant() %>">
                            <%= "*".repeat(w.getCnpjPlant() != null ? w.getCnpjPlant().length() : 0) %>
                        </td>
                        <td>
                            <div class="actions">
                                <form action="show" method="get">
                                    <button type="button" class="see toggle-visibility">
                                        <img class="eye open" src="${pageContext.request.contextPath}/img/icons/ui/eye%20(open).png" alt="Mostrar">
                                        <img class="eye closed" src="${pageContext.request.contextPath}/img/icons/ui/eye%20(closed).png" alt="Ocultar">
                                    </button>
                                </form>

                                <form class="create" action="${pageContext.request.contextPath}/worker/render-update" method="post">
                                    <input type="hidden" name="cpf" value="<%= w.getCpf() %>">
                                    <button type="submit">
                                        <img src="${pageContext.request.contextPath}/img/icons/ui/pencil%20(black).png" alt="Editar">
                                    </button>
                                </form>
                                
                                <form class="delete" style="opacity: 0; cursor: default;">
                                    <input style="opacity: 0; cursor: default;" type="hidden" name="cpf" value="<%= w.getCpf() %>">
                                    <button style="opacity: 0; cursor: default;" type="submit">
                                        <img style="opacity: 0; cursor: default;" src="${pageContext.request.contextPath}/img/icons/ui/trash%20(black).png" alt="Deletar">
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <%
                                }
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7">Nenhum trabalhador foi encontrado</td>
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
            <img src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND%20(white).png" alt="HIVEMIND">
        </button>
    </a>
    <nav id="redirects" class="inter navbar">
        <a href="${pageContext.request.contextPath}/pages/aboutUs.html" target="_blank">Quem somos</a>
        <a href="${pageContext.request.contextPath}/index.html">Home</a>
        <a href="${pageContext.request.contextPath}/pages/workerLogin.jsp">Gestão de trabalhadores</a>
    </nav>
    <nav class="medias">
        <a href="#"><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
        <a href="#"><img src="${pageContext.request.contextPath}/img/icons/social/whatsapp.png" alt="whatsapp" class="icon"></a>
        <a href="#"><img src="${pageContext.request.contextPath}/img/icons/social/instagram.png" alt="instagram" class="icon"></a>
    </nav>
    <p id="copy-right" class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
</footer>

<script src="${pageContext.request.contextPath}/js/dataReal.js"></script>
</body>
</html>
