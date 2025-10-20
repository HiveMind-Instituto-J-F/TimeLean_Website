package hivemind.hivemindweb.Servelts.Login;

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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private Jedis jedis;

    @Override
    public void init() throws ServletException {
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String sessionKey = "session:" + session.getId();
            jedis.del(sessionKey);       // Remove do Redis
            session.invalidate();        // invalidate Session HTTP
        }
        resp.sendRedirect(req.getContextPath() + "/html/login.jsp"); // manda pro login
    }

    @Override
    public void destroy() {
        if (jedis != null) jedis.close();
    }
}
