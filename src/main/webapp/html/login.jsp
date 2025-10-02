<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/text.css">
    <link rel="stylesheet" href="../css/others/login.css">

    <title>Login — TIMELEAN</title>
    <link rel="shortcut icon" href="../img/favicon/home-v2.png" type="image/x-icon">
</head>
<body>
    <div id="major">
        <div id="img"></div>
        <div id="form">
            <h1 class="inter-bold">Fazer Login</h1>
            <p class="inter-thin">Lorem ipsum dolor sit amet consectetur adipisicing elit. Assumenda.</p>
            
            <!-- Displays error message if login fails -->
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <div style="color: red; margin-bottom: 15px;" class="inter-thin">
                    <%= errorMessage %>
                </div>
            <% } %>
            
            <form action="/HivemindWeb_war/login" method="post" class="inter-thin">
                <div>
                    <p>Login</p>
                    <input name="email" type="text" placeholder="Insira seu usuário">
                </div>
                <div>
                    <p>Senha</p>
                    <input name="password" type="password" placeholder="Insira sua senha">
                </div>
                <div id="show">
                    <input type="checkbox">
                    <p name="show">Mostrar senha</p>
                </div>
                <button type="submit" class="button inter">Fazer login</button>
            </form>
        </div>
    </div>
</body>
</html>