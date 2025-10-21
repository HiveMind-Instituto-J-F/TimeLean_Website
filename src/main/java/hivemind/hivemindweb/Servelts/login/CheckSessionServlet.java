package hivemind.hivemindweb.Servelts.Login;


import java.io.IOException;

import com.sun.tools.jconsole.JConsoleContext;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

        resp.setHeader("Access-Control-Allow-Origin", "https://area-restrita-5xoh.onrender.com");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setContentType("application/json");

        try {
            HttpSession session = req.getSession(false);

            if (session == null) {
                resp.getWriter().write("{\"loggedIn\": false}");
                System.out.println("[INFO] Não há sessão ativa (CheckSessionServlet).");
                return;
            }

            String sessionKey = "session:" + session.getId();
            boolean exists = jedis.exists(sessionKey);


            if (exists && "true".equals(jedis.hget(sessionKey, "logged"))) {
                String email = (String) jedis.hget(sessionKey, "email");
                System.out.println("[INFO] Usuário logado (CheckSessionServlet): " + email);
                resp.getWriter().write("{\"loggedIn\":true,\"email\":\"" + email + "\"}");
            } else {
                resp.getWriter().write("{\"loggedIn\": false}");
                System.out.println("[INFO] Usuário não logado (CheckSessionServlet).");
            }

        } catch (Exception e) {
            System.out.println("[ERROR] CheckSession: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao verificar sessão: " + e.getMessage());
        }
    }
}
