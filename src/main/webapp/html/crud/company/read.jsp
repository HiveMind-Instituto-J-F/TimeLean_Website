<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TIMELEAN</title>
    <link rel="shortcut icon" href="../img/favicon/home-v2.png" type="image/x-icon">
</head>
<body>
<h1>Relacao COMPANY</h1>
<h2>Companies List</h2>
<a href="<%= request.getContextPath() %>/html/crud/company/create.jsp">Add company</a>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>CNPJ</th>
        <th>Name</th>
        <th>CNAE</th>
        <th>REGISTRANT_CPF</th>
        <th>Actions</th>
    </tr>

    <%
        List<Company> companies = (List<Company>) request.getAttribute("companies");
        if (companies != null) {
            for (Company c : companies) {
    %>
    <tr>
        <td><%= c.getCNPJ() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.getCnae() %></td>
        <td><%= c.getRegistrantCpf() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/company/delete" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Delete"/>
            </form>
            <form action="${pageContext.request.contextPath}/company/render-update" method="get" style="display:inline;">
                <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>"/>
                <input type="submit" value="Modify"/>
            </form>
        </td>
    </tr>
    <%
            }
    } else {
    %>
    <tr>
        <td colspan="7">No companies found</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
=======
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="hivemind.hivemindweb.models.Company" %>
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
        <h3 class="inter-medium">Bem vindo ao CRUD.</h3>
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
        <h1 class="inter-bold">Empresas Cadastradas</h1>

        <div id="filter-bar" class="inter">
            <form action="${pageContext.request.contextPath}/company/read" method="get">
                <select class="inter" id="status" name="status">
                    <option value="all-companies">Todas</option>
                    <option value="active-companies">Apenas empresas ativas</option>
                    <option value="inactive-companies">Apenas empresas inativas</option>
                    <option value="companies-with-pending-payments">Apenas empresas com pagamentos pendentes</option>
                </select>
                <button class="inter" type="submit">Filtrar</button>
            </form>

            <a href="${pageContext.request.contextPath}/html/crud/company/create.jsp">
                <button class="inter" type="submit">Cadastrar +</button>
            </a>

        </div>

        <table>
            <thead class="inter-hard">
            <tr>
                <th>CNPJ</th>
                <th>Nome</th>
                <th>CNAE</th>
                <th>Registrante</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody class="inter-thin">
            <%
                List<Company> companies = (List<Company>) request.getAttribute("companies");
                if (companies != null && !companies.isEmpty()) {
                    for (Company c : companies) {
                        boolean active = c.isActive();
            %>
            <tr class="<%= active ? "" : "disable" %>">
                <td><%= c.getCNPJ() %></td>
                <td><%= c.getName() %></td>
                <td><%= c.getCnae() %></td>
                <td><%= c.getRegistrantCpf() %></td>
                <td><%= active ? "Ativa" : "Desativada" %></td>
                <td>
                    <% if (active) { %>
                    <div class="actions">
                        <form action="${pageContext.request.contextPath}/company/show" method="get">
                            <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>">
                            <button type="submit" class="see">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/eye.png" alt="Mostrar">
                            </button>
                        </form>
                        <form class="create" action="${pageContext.request.contextPath}/company/render-update" method="get">
                            <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>">
                            <button type="submit">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/pencil (black).png" alt="Editar">
                            </button>
                        </form>
                        <form class="delete" action="${pageContext.request.contextPath}/company/delete" method="get">
                            <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>">
                            <button type="submit">
                                <img src="${pageContext.request.contextPath}/img/icons/ui/trash (black).png" alt="Deletar">
                            </button>
                        </form>
                    </div>
                    <% } else { %>
                    <form action="${pageContext.request.contextPath}/company/delete/rollback" method="post">
                        <input type="hidden" name="cnpj" value="<%= c.getCNPJ() %>">
                        <input type="submit" value="Reativar"/>
                    </form>
                    <% } %>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6">Nenhuma empresa encontrada.</td>
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
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
