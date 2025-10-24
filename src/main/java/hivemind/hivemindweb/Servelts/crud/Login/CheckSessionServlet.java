package hivemind.hivemindweb.Servelts.crud.Login;


import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

@WebServlet("/checkSession")
public class CheckSessionServlet extends HttpServlet {

    private Jedis jedis;

    @Override
    public void init() throws ServletException {
        super.init();

        Dotenv dotenv = (Dotenv) getServletContext().getAttribute("data");
        if(dotenv == null){
            System.out.println("[ERROR] Dotenv Is null in Servelet Context");
            return;
        }

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password(dotenv.get("rds_password"))
                .build();
                
        String host = dotenv.get("rds_host");
        HostAndPort hostAndPort = new HostAndPort(host,17579);

        jedis = new Jedis(hostAndPort, config);
        System.out.println("[INFO] Conectado ao Redis remoto (CheckSessionServlet).");
    }

    @Override
    public void destroy() {
        if (jedis != null) {
            jedis.close();
            System.out.println("[INFO] Conexão Redis fechada (CheckSessionServlet).");
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        try {
            HttpSession session = req.getSession(false);

            if (session == null) {
                resp.getWriter().write("{\"loggedIn\": false}");
                return;
            }

            String sessionKey = "session:" + session.getId();
            boolean exists = jedis.exists(sessionKey);

            if (exists && "true".equals(jedis.hget(sessionKey, "logged"))) {
                resp.getWriter().write("{\"loggedIn\": true}");
            } else {
                resp.getWriter().write("{\"loggedIn\": false}");
            }

        } catch (Exception e) {
            System.out.println("[ERROR] CheckSession: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao verificar sessão: " + e.getMessage());
        }
    }
}
