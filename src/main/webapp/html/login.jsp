<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Login — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/others/login.css">
</head>

<body>
    <div id="div-img"></div>
    <div id="major">
        <div>
            <div class="block"></div>
            <h1 class="inter-bold">Fazer Login</h1>
        </div>
        
        <form action="/HivemindWeb_war/login" method="post" class="inter-thin">
            <div class="input-div">
                <p>Login</p>
                <div>
                    <input name="email" type="email" placeholder="Ex: timelean@hotmail.com" required
                    pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$">
                </div>
            </div>
            <div class="input-div">
                <p>Senha</p>
                <div>
                    <input name="password" class="password" type="password" placeholder="Insira sua senha aqui" required>
                    <button name="toggle" type="button">
                        <img id="open" src="${pageContext.request.contextPath}/img/icons/ui/eye (open).png" alt="mostrar senha">
                        <img id="closed" src="${pageContext.request.contextPath}/img/icons/ui/eye (closed).png" alt="ocultar senha">
                    </button>
                </div>
            </div>

            <!-- Displays error message if login fails -->
        
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <div style="color: red; text-align: center;" class="inter-thin">
                    <%= errorMessage %>
                </div>
            <% } %>

            <button type="submit" class="button submit inter">Fazer login</button>
        </form>
    </div>
</body>
<script src="../js/password.js"></script>
</html>