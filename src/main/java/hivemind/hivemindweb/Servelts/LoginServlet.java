package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import javax.security.auth.login.LoginException;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.config.EnvLoader;
import hivemind.hivemindweb.models.Admin;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.DefaultJedisClientConfig;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Jedis jedis;

    @Override
    public void init() throws ServletException {
        super.init();
        Dotenv dotenv = EnvLoader.getDotenv();


        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password(dotenv.get("rds_password"))
                .build();

        HostAndPort hostAndPort = new HostAndPort(
                dotenv.get("rds_host"),
                17579
        );

        jedis = new Jedis(hostAndPort, config);

        System.out.println("[INFO] Conectado ao Redis remoto.");
    }

    @Override
    public void destroy() {
        if (jedis != null) {
            jedis.close();
            System.out.println("[INFO] Conexão Redis fechada.");
        }
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("[WARN] Open LoginServlet");
            System.out.println("[WARN] Method Use In Servlet: " + req.getMethod());

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Email ou senha incorretos");
            }

            Admin adminClient = new Admin(email, password);

            // Criar HTTP Session
            HttpSession session = req.getSession(true);
            session.setMaxInactiveInterval(600);

            if (AuthService.login(adminClient)) {
                // Salva login no Redis
                String sessionKey = "session:" + session.getId();
                jedis.hset(sessionKey, "email", email);
                jedis.hset(sessionKey, "logged", "true");
                jedis.expire(sessionKey, 600); // expira em 10 minutos

                session.setAttribute("user", adminClient);
                session.setAttribute("login", true);
                System.out.println("[INFO] Login Successful - Salvo no Redis remoto com chave: " + sessionKey);

                req.getRequestDispatcher("/html/crud/toUser.html").forward(req, resp);
            } else {
                session.setAttribute("login", false);
                throw new LoginException("Email ou senha incorretos.");
            }

        } catch (IllegalArgumentException ime) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos ou nulos.");
            System.out.println("[ERROR] Invalid User");
            req.setAttribute("error", ime.getMessage());
            req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
        } catch (LoginException le) {
            req.setAttribute("errorMessage", le.getMessage());
            req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
        } catch (Exception e) {
            System.out.println("[ERROR] Exception: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }
}