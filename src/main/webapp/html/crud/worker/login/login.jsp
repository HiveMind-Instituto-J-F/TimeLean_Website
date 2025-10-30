<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <div id="major" style="height: 80vh;">
        <div>
            <div class="block"></div>
            <h1 class="inter-bold">Fazer Login</h1>
        </div>
        
        <form action="${pageContext.request.contextPath}/worker/login" method="post" class="inter-thin">
            <div class="input-div">
                <p>CNPJ da planta industrial</p>
                <div>
                    <input name="plant-cnpj" type="text" placeholder="Ex: 71.973.528/0001-95"
                    pattern="^[0-9]{14}$" required>
                </div>
            </div>
            <div class="input-div">
                <p>CPF do responsável</p>
                <div>
                    <input name="plant-responsible-cpf" type="text" placeholder="Ex: 938.421.850-22"
                    pattern="^[0-9]{11}$" required>
                </div>
            </div>
            <div class="input-div">
                <p>E-mail do responsável</p>
                <div>
                    <input name="plant-responsible-login-email" type="email" placeholder="Ex: timelean@hotmail.com"
                    pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required>
                </div>
            </div>
            <div class="input-div input-password">
                <p>Senha</p>
                <div>
                    <input name="plant-responsible-login-password" class="password" type="password" placeholder="Insira sua senha aqui">
                    <button name="toggle" type="button">
                        <img id="open" src="${pageContext.request.contextPath}/img/icons/ui/eye (open).png" alt="mostrar senha">
                        <img id="closed" src="${pageContext.request.contextPath}/img/icons/ui/eye (closed).png" alt="ocultar senha">
                    </button>
                </div>
            </div>
            <%
            if ((Boolean) request.getAttribute("status") != null) {
                if ((Boolean) request.getAttribute("status") == false){
                    %>
                    <p style="text-align: center; color: red;">Credenciais incorretas</p>
                    <%
                }
            }
            %>
            <button type="submit" class="button submit inter">Fazer login</button>
        </form>
    </div>
</body>
<script src="${pageContext.request.contextPath}/js/password.js"></script>
</html>