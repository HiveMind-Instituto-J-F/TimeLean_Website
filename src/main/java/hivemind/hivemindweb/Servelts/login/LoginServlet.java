package hivemind.hivemindweb.Servelts.login;

import java.io.IOException;

import hivemind.hivemindweb.Servelts.login.AuthService;
import hivemind.hivemindweb.models.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private JedisPool jedisPool;

    @Override
    public void init() throws ServletException {
        super.init();
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                .user("default")
                .password("C58Gp6idHQgpNRTxq0iVtbA0pockI25i")
                .build();
        HostAndPort hostAndPort = new HostAndPort(
                "redis-17579.c73.us-east-1-2.ec2.redns.redis-cloud.com",
                17579
        );
        jedisPool = new JedisPool(new JedisPoolConfig(), hostAndPort, clientConfig);
        System.out.println("[INFO] JedisPool configurado (LoginServlet).");
    }

    // CORS preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Permitir CORS
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"message\": \"Email ou senha não podem estar vazios.\"}");
            System.out.println("[WARN] LoginServlet: Email ou senha não podem estar vazios.");
            return;
        }

        try {
            Admin adminFromDB = AuthService.login(email, password);

            if (adminFromDB != null) {
                // Login válido: cria sessão e salva no Redis
                HttpSession session = req.getSession(true);
                session.setMaxInactiveInterval(600); // 10 minutos

                String sessionKey = "session:" + session.getId();
                try (Jedis jedis = jedisPool.getResource()) {
                    jedis.hset(sessionKey, "email", email);
                    jedis.hset(sessionKey, "logged", "true");
                    jedis.expire(sessionKey, 86400);
                }

                session.setAttribute("user", adminFromDB);
                session.setAttribute("login", true);

                System.out.println("[INFO] LoginServlet: Login efetuado com sucesso para " + email);
                resp.sendRedirect("html/chooser.html");
            } else {

                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"success\": false, \"message\": \"Email ou senha incorretos.\"}");
                System.out.println("[WARN] LoginServlet: Falha no login para " + email);
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
            System.out.println("[ERROR] LoginServlet Exception: " + e.getMessage());
        }
    }
}
