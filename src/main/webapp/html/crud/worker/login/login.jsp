<html lang="pt-br">
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
            <h1 class="inter-bold">Fazer Login - Painel Operacional</h1>
            
            <form action="/worker/login" method="post" class="inter-thin">
                <div>
                    <label for="plant-cnpj">Digite o CNPj da planta industrial:</label>
                    <input name="plant-cnpj" type="text" placeholder="Ex: 012345678911234">
                </div>
                <div>
                    <label for="plant-responsible-cpf">Digite o CPF do responsavel:</label>
                    <input name="plant-responsible-cpf" type="text">
                </div>
                <div>
                    <label for="plant-responsible-login-email">Digite o email do responsavel:</label>
                    <input name="plant-responsible-login-email" type="text">
                </div>
                <div>
                    <label for="plant-responsible-login-password">Digite a senha do responsavel:</label>
                    <input name="plant-responsible-login-password" type="password">
                </div>
                <div id="show">
                    <input type="checkbox">
                    <p name="show">Mostrar senha</p>
                </div>
                <button type="submit" class="button inter">Fazer login</button>
            </form>
            <%
            if ((Boolean) request.getAttribute("status") != null) {
                if ((Boolean) request.getAttribute("status") == false){
                    %>
                    <p>Incorrect credentials</p>
                    <%
                }
            }
            %>
        </div>
    </div>
</body>
</html>
