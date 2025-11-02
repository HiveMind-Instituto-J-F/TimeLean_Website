<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon/home-v2.png" type="image/x-icon">
    <title>Contato — TIMELEAN</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/others/contact.css">
</head>

<body>
    <div class="wrap" id="background-universal">
        <header class="blur">
            <a href="#" class="branding">
                <img src="${pageContext.request.contextPath}/img/icons/branding/TIMELEAN (black).png" alt="TIMELEAN">
            </a>

            <div id="redirects">
                <nav class="inter navbar">
                    <a href="${pageContext.request.contextPath}/index.html">Home</a>
                    <a href="${pageContext.request.contextPath}/pages/aboutUs.html">Quem somos</a>
                    <a href="${pageContext.request.contextPath}/pages/workerLogin.jsp">Gestão de trabalhadores</a>
                </nav>
            </div>
        </header>
        
        <main>
            <section>
                <div>
                    <div class="block"></div>
                    <h1 class="inter-bold">Entre em contato</h1>
                </div>
                
                <ul class="inter-thin">
                    <li>
                        <h3 class="inter-medium">Nos contate</h3>
                        <p>Contate diretamente nossa equipe, informe o setor de atuação de sua empresa, o e-mail de retorno e o assunto do contato. Nossa equipe pode demorar alguns dias para o retorno do contato.</p>
                    </li>
                    <li>
                        <h3 class="inter-medium">E-mail corporativo</h3>
                        <p>hivemind@hotmail.com</p>
                    </li>
                    <li>
                        <h3 class="inter-medium">Interesse em aquisição de nosso produto</h3>
                        <p>timelean@hotmail.com</p>
                    </li>
                </ul>
            </section>
            
            <div id="wrap">
                <form class="inter-thin" action="${pageContext.request.contextPath}/email/send" method="get">
                    <div class="input-div">
                        <label for="sector">Setor</label>
                        <input name="sector" type="text" placeholder="Ex: Industrias Têxteis" required>
                    </div>
                    
                    <div class="input-div">
                        <label for="sender">E-mail de retorno</label>
                        <input name="sender" type="email" placeholder="Ex: timelean@hotmail.com" required
                        pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
                    </div>
                    
                    <div class="input-div">
                        <label for="subject">Assunto</label>
                        <input name="subject" type="text" placeholder="Insira o título do e-mail aqui" required>
                    </div>
                    
                    <div class="input-div">
                        <label for="msg">Mensagem</label>
                        <textarea class="inter" name="msg" placeholder="Insira sua mensagem aqui"></textarea>
                    </div>
                    <% String errorMessage = (String) request.getAttribute("error"); %>
                        <% if (errorMessage != null) { %>
                            <div style="color: red; text-align: center;" class="inter-thin">
                                <%= errorMessage %>
                            </div>
                    <% } %>
                    <% String msg = (String) request.getAttribute("msg"); %>
                        <% if (msg != null) { %>
                            <div style="color: rgb(54, 192, 54); text-align: center;" class="inter-thin">
                                <%= msg %>
                            </div>
                    <% } %>
                    <button class="button inter" type="submit">Enviar email</button>
                </form>
            </div>
        </main>
    </div>

    <footer class="inter-thin">
        <a style="cursor: none;">
            <button class="branding" id="hivemind">
                <img src="${pageContext.request.contextPath}/img/icons/branding/HIVEMIND (white).png" alt="HIVEMIND">
            </button>
        </a>
        <nav id="redirects" class="inter navbar">
            <a href="${pageContext.request.contextPath}/pages/aboutUs.html">Quem somos</a>
            <a href="${pageContext.request.contextPath}/index.html">Home</a>
            <a href="${pageContext.request.contextPath}/pages/workerLogin.jsp">Gestão de trabalhadores</a>
        </nav>
        <nav class="medias">
            <a href="https://github.com/HiveMind-Instituto-J-F/TimeLean_Website"><img src="${pageContext.request.contextPath}/img/icons/social/github.png" alt="github" class="icon"></a>
        </nav>
        <p id="copy-right" class="inter">&copy; Copyright 2025, Todos os direitos preservados</p>
    </footer>
</body>

</html>