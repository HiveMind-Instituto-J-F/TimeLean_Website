<%--
  Created by IntelliJ IDEA.
  User: eduardomacal-ieg
  Date: 17/10/2025
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
  <title>Ocorreu um erro — TIMELEAN</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/others/error.css">
</head>

<body id="background-universal">
  <div>
    <h1 class="inter-bold">Ops...</h1>
    <p class="inter-thin">Ocorreu um erro</p>
    <h3 class="inter-medium"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "An unknown error occurred." %></h3>
    <a href="<%=request.getAttribute("errorUrl")%>">
        <button class="button inter">Voltar</button>
    </a>
  </div>
</body>

</html>