package hivemind.hivemindweb.Servelts.Login;

import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/checkSession")
public class CheckSessionServlet extends HttpServlet {

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
                return;
            }

            String sessionKey = "session:" + session.getId();
            boolean exists = RedisManager.exists(sessionKey, dotenv);
            String logged = RedisManager.getField(sessionKey, "logged", dotenv);

            if (exists && "true".equals(logged)) {
                String email = RedisManager.getField(sessionKey, "email", dotenv);
                resp.getWriter().write("{\"loggedIn\":true,\"email\":\"" + email + "\"}");
            } else {
                resp.getWriter().write("{\"loggedIn\": false}");
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao verificar sessão: " + e.getMessage());
        }
    }
}
