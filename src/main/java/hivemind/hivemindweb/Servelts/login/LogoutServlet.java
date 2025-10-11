package hivemind.hivemindweb.Servelts.login;

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

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private Jedis jedis;

    @Override
    public void init() throws ServletException {
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password("C58Gp6idHQgpNRTxq0iVtbA0pockI25i")
                .build();
        HostAndPort hostAndPort = new HostAndPort(
                "redis-17579.c73.us-east-1-2.ec2.redns.redis-cloud.com",
                17579
        );
        jedis = new Jedis(hostAndPort, config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String sessionKey = "session:" + session.getId();
            jedis.del(sessionKey);       // Remove do Redis
            session.invalidate();        // Invalida sessão HTTP
        }
        resp.sendRedirect(req.getContextPath() + "/html/login.jsp"); // manda pro login
    }

    @Override
    public void destroy() {
        if (jedis != null) jedis.close();
    }
}
