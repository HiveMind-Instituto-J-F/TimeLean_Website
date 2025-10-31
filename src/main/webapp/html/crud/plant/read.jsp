<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Plant" %>

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
    <title>Pagamentos — TIMELEAN</title>

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
    <!-- Wrap para o Background ↓ -->
    <div id="background-img">
        <header class="blur">
            <a href="#" class="branding">
                <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN (black).png" alt="TIMELEAN">
            </a>

            <div id="redirects">
                <nav class="inter navbar">
                    <a href="${pageContext.request.contextPath}/index.html">Home</a>
                    <a href="" target="_blank">Quem somos</a>
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
                <a href="${pageContext.request.contextPath}/html/email/send.jsp" target="_blank">
                    <h3>Entrar em contato</h3>
                </a>
            </div>
        </aside>

        <!-- CRUD ↓ -->
        <section> <!-- Desabilitar o display -->
            <div class="block"></div>
            <h1 class="inter-bold">Plantas Industriais Cadastradas</h1>

            <!-- Filtro e submit ↓ -->
            <div id="filter-bar" class="inter">
                <form action="${pageContext.request.contextPath}/plant/read" method="get">
                    <div>
                        <label for="status">
                            <img src="${pageContext.request.contextPath}/img/icons/ui/filter.png" alt="filtrar">
                        </label>
                        <select class="inter" id="status" name="status">
                            <option value="all-companies">Todas</option>
                            <option value="active-companies">Apenas empresas ativas</option>
                            <option value="inactive-companies">Apenas empresas inativas</option>
                            <option value="companies-with-pending-payments">Apenas empresas com pagamentos pendentes</option>
                        </select>
                    </div>
                    <button class="inter" type="submit">Submeter</button>
                </form>
                <div>
                    <a class="inter" href="${pageContext.request.contextPath}/html/crud/plant/create.jsp">Cadastrar
                        nova planta</a>
                </div>
            </div>

            <!-- Table ↓ -->
            <div id="wrap">
                <table>
                    <!-- Theader ↓ -->
                    <thead class="inter-hard">
                        <tr>
                            <th>CNPJ</th>
                            <th>CNAE</th>
                            <th>CPF do responsável</th>
                            <th>Número do endereço</th>
                            <th>CEP</th>
                            <th>CNPJ da companhia</th>
                            <th>Status operacional</th>
                            <th>Ações</th>
                        </tr>
                    </thead>

                    <!-- Estrutura principal ↓ -->
                    <tbody class="inter-thin">
                        <%
                            List<Plant> plantList = (List<Plant>) request.getAttribute("plantList");
                            if (plantList != null && !plantList.isEmpty()) {
                                for (Plant p : plantList) {
                                    boolean active = p.getOperationalStatus();
                        %>
                        <tr>
                            

                            <td class="sensitive" data-real="<%= p.getCnpj() %>">
                                <%= "*".repeat(p.getCnpj().length()) %>
                            </td>

                            <td class="sensitive" data-real="<%= p.getCnae() %>">
                                <%= "*".repeat(p.getCnae().length()) %>
                            </td>

                            <td class="sensitive" data-real="<%= p.getResponsibleCpf() %>">
                                <%= "*".repeat(p.getResponsibleCpf().length()) %>
                            </td>

                            <td class="sensitive" data-real="<%= p.getAddressNumber() %>">
                                <%= "*".repeat(String.valueOf(p.getAddressNumber()).length()) %>
                            </td>

                            <td class="sensitive" data-real="<%= p.getAddressCep() %>">
                                <%= "*".repeat(p.getAddressCep().length()) %>
                            </td>

                            <td class="sensitive" data-real="<%= p.getCnpjCompany() %>">
                                <%= "*".repeat(p.getCnpjCompany().length()) %>
                            </td>

                            <td><%= p.getOperationalStatus() ? "Ativo" : "Inativo" %></td>
                        
                            <td>
                                <% if (active) { %>
                                <div class="actions">
                                    <form action="show" method="get">
                                        <button type="button" class="see toggle-visibility">
                                            <img class="eye open" src="${pageContext.request.contextPath}/img/icons/ui/eye (open).png"
                                                alt="Mostrar">
                                            <img class="eye closed" src="${pageContext.request.contextPath}/img/icons/ui/eye (closed).png"
                                                alt="Ocultar">
                                        </button>
                                    </form>

                                    <form class="create" action="${pageContext.request.contextPath}/plant/render-update" method="get">
                                        <input type="hidden" name="cnpj" value="<%= p.getCnpj() %>">
                                        <button type="submit">
                                            <img src="${pageContext.request.contextPath}/img/icons/ui/pencil (black).png" alt="Editar">
                                        </button>
                                    </form>
                                    
                                    <form class="delete" action="${pageContext.request.contextPath}/plant/delete" method="get">
                                        <button type="submit">
                                            <input type="hidden" name="cnpj" value="<%= p.getCnpj() %>">
                                            <img src="${pageContext.request.contextPath}/img/icons/ui/trash (black).png" alt="Deletar">
                                        </button>
                                    </form>
                                    <% } else { %>
                                    <form action="${pageContext.request.contextPath}/plant/delete/rollback" method="post" style="display:inline;">
                                        <input type="hidden" name="cnpj" value="<%= p.getCnpj() %>"/>
                                        <input type="submit" value="Reativar"/>
                                    </form>
                                    <% } %>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="8">Nenhuma planta industrial foi encontrada.</td>
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
            <a href="" target="_blank">Quem somos</a>
            <a href="${pageContext.request.contextPath}/index.html" target="">Home</a>
            <a href="${pageContext.request.contextPath}/html/crud/worker/login/login.jsp">Gestão de trabalhadores</a>
        </nav>
        <nav class="medias">
            <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
            <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/whatsapp.png" alt="whatsapp" class="icon"></a>
            <a href=""><img src="${pageContext.request.contextPath}/img/icons/social/instagram.png" alt="instagram" class="icon"></a>
        </nav>
        <p id="copy-right" class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
    </footer>
</body>

<script src="${pageContext.request.contextPath}/js/dataReal.js"></script>

</html>