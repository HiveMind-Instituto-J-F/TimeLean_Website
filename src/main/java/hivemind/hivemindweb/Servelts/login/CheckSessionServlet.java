package hivemind.hivemindweb.Servelts.login;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import redis.clients.jedis.*;

@WebServlet("/checkSession")
public class CheckSessionServlet extends HttpServlet {

    private JedisPool jedisPool;

    @Override
    public void init() throws ServletException {
        super.init();

        // Redis client configuration
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password("C58Gp6idHQgpNRTxq0iVtbA0pockI25i")
                .build();

        HostAndPort hostAndPort = new HostAndPort(
                "redis-17579.c73.us-east-1-2.ec2.redns.redis-cloud.com",
                17579
        );

        // Create the pool (recommended)
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(50);

        jedisPool = new JedisPool(poolConfig, hostAndPort, config);
        System.out.println("[INFO] JedisPool configurado (CheckSessionServlet).");
    }

    @Override
    public void destroy() {
        if (jedisPool != null) {
            jedisPool.close();
            System.out.println("[INFO] JedisPool fechado (CheckSessionServlet).");
        }
        super.destroy();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (Jedis jedis = jedisPool.getResource()) {
            HttpSession session = req.getSession(false);

            if (session == null) {
                resp.getWriter().write("{\"loggedIn\": false}");
                return;
            }

            String sessionKey = "session:" + session.getId();
            boolean exists = jedis.exists(sessionKey);

            if (exists && "true".equals(jedis.hget(sessionKey, "logged"))) {
                String email = jedis.hget(sessionKey, "email");
                resp.getWriter().write("{\"loggedIn\": true, \"email\": \"" + email + "\"}");
            } else {
                resp.getWriter().write("{\"loggedIn\": false}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"loggedIn\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
