package hivemind.hivemindweb.Servelts.crud.Login;

import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private Dotenv dotenv;

    @Override
    public void init() throws ServletException {
        dotenv = (Dotenv) getServletContext().getAttribute("data");
        if (dotenv == null) {
            System.out.println("[ERROR] Dotenv is null in ServletContext");
        } else {
            RedisManager.initialize(dotenv);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String sessionKey = "session:" + session.getId();
            RedisManager.delete(sessionKey, dotenv);
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/html/login.jsp");
    }

    @Override
    public void destroy() {
        RedisManager.close();
    }
}
