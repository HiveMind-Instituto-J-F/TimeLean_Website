package hivemind.hivemindweb.Servlets.crud.Login;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.security.auth.login.LoginException;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.Connection.RedisManager;
import hivemind.hivemindweb.models.Admin;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Dotenv dotenv;

    @Override
    public void init() throws ServletException {
        super.init();
        dotenv = (Dotenv) getServletContext().getAttribute("data");
        if (dotenv == null) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Dotenv is null in ServletContext");
        } else {
            RedisManager.initialize(dotenv);
        }
    }

    @Override
    public void destroy() {
        RedisManager.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // [VALIDATION] Retrieve and validate parameters
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'email'");
            }
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'password'");
            }

            // [PROCESS] Create admin object and session
            Admin adminClient = new Admin(email, password);
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(600);

            // [BUSINESS RULES] Authenticate user
            if (AuthService.login(adminClient)) {

                // [DATA ACCESS] Save session info to Redis
                String sessionKey = "session:" + session.getId();
                RedisManager.save(sessionKey, "email", email, dotenv);
                RedisManager.save(sessionKey, "logged", "true", dotenv);
                RedisManager.expire(sessionKey, 600, dotenv);

                session.setAttribute("user", adminClient);
                session.setAttribute("login", true);

                // [PROCESS] Set secure session cookie
                Cookie cookie = new Cookie("JSESSIONID", session.getId());
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setAttribute("SameSite", "None");
                response.addCookie(cookie);

                // [SUCCESS LOG] Login successful
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Login successful for email: " + email);
                request.getRequestDispatcher("/pages/chooser.jsp").forward(request, response);

            } else {
                session.setAttribute("login", false);
                throw new LoginException("Email ou senha incorretos.");
            }

        } catch (IllegalArgumentException | LoginException e) {
            // [FAILURE LOG] User input or authentication failure
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Exception: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro interno: " + e.getMessage());
            request.getRequestDispatcher("/pages/error/error.jsp").forward(request, response);
        }
    }
}
