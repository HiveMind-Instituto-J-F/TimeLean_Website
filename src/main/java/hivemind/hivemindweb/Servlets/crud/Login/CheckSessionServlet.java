package hivemind.hivemindweb.Servlets.crud.Login;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.Connection.RedisManager;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkSession")
public class CheckSessionServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "https://area-restrita-5xoh.onrender.com");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.getWriter().write("{\"loggedIn\": false}");
                return;
            }

            String sessionKey = "session:" + session.getId();
            boolean sessionExists = RedisManager.exists(sessionKey, dotenv);
            String loggedStatusFromDb = RedisManager.getField(sessionKey, "logged", dotenv);

            if (sessionExists && "true".equals(loggedStatusFromDb)) {
                String emailFromDb = RedisManager.getField(sessionKey, "email", dotenv);
                String avatarFromDb = RedisManager.getField(sessionKey, "image", dotenv);

                if (emailFromDb == null) emailFromDb = "";
                if (avatarFromDb == null) avatarFromDb = "";

                String jsonResponse = String.format(
                        "{\"loggedIn\":true,\"email\":\"%s\",\"avatar\":\"%s\"}",
                        emailFromDb,
                        avatarFromDb
                );

                response.getWriter().write(jsonResponse);

                System.out.println("[INFO] [" + LocalDateTime.now() + "] Session OK | email: "
                        + emailFromDb + " | avatar: " + avatarFromDb);

            } else {
                response.getWriter().write("{\"loggedIn\": false}");
                System.out.println("[INFO] [" + LocalDateTime.now() + "] No active session");
            }

        } catch (Exception e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getMessage());
            response.getWriter().write("{\"loggedIn\": false, \"error\": \"internal_error\"}");
        }
    }
}
