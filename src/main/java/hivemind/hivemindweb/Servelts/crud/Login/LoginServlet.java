package hivemind.hivemindweb.Servelts.crud.Login;

import java.io.IOException;
import javax.security.auth.login.LoginException;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.models.Admin;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Dotenv dotenv;

    @Override
    public void init() throws ServletException {
        super.init();
        dotenv = (Dotenv) getServletContext().getAttribute("data");
        if (dotenv == null) {
            System.out.println("[ERROR] Dotenv is null in ServletContext");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Email ou senha incorretos");
            }

            Admin adminClient = new Admin(email, password);
            HttpSession session = req.getSession(true);
            session.setMaxInactiveInterval(600);

            if (AuthService.login(adminClient)) {
                String sessionKey = "session:" + session.getId();
                RedisManager.save(sessionKey, "email", email, dotenv);
                RedisManager.save(sessionKey, "logged", "true", dotenv);
                RedisManager.expire(sessionKey, 600, dotenv);

                session.setAttribute("user", adminClient);
                session.setAttribute("login", true);

                Cookie cookie = new Cookie("JSESSIONID", session.getId());
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setAttribute("SameSite", "None");
                resp.addCookie(cookie);

                System.out.println("[INFO] Login Successful - Sessão salva no Redis: " + sessionKey);
                req.getRequestDispatcher("/html/crud/toUser.html").forward(req, resp);
            } else {
                session.setAttribute("login", false);
                throw new LoginException("Email ou senha incorretos.");
            }

<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelts/Login/LoginServlet.java
        } catch (IllegalArgumentException | LoginException e) {
            req.setAttribute("errorMessage", e.getMessage());
=======
        } catch (IllegalArgumentException ime) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos ou nulos.");
            System.err.println("[ERROR] Invalid User");
            req.setAttribute("error", ime.getMessage());
            req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
        } catch (LoginException le) {
            req.setAttribute("errorMessage", le.getMessage());
>>>>>>> 27f05bbfde5778f81e88d70aa5b6436e446639ef:src/main/java/hivemind/hivemindweb/Servelts/crud/Login/LoginServlet.java
            req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
        } catch (Exception e) {
            System.err.println("[ERROR] Exception: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
