<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/text.css">
    <link rel="stylesheet" href="../../css/others/login.css">

    <title>ERRO — TIMELEAN</title>
    <link rel="shortcut icon" href="../../img/favicon/home-v2.png" type="image/x-icon">
</head>
<body>
    <div class="content">
        <h1>ERROR</h1>
        <p>Desculpe Mais Tivemos Ploblemas internos, Erro: <% exception.printStackTrace(new java.io.PrintWriter(out)); %></p>
    </div>
    
</body>
</html>