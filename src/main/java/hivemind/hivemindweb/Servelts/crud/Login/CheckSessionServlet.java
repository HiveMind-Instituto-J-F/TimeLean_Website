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
        // [PROCESS] Retrieve dotenv from context and initialize Redis
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

        // [PROCESS] Set CORS headers and content type
        response.setHeader("Access-Control-Allow-Origin", "https://area-restrita-5xoh.onrender.com");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");

        try {
            // [VALIDATION] Check if session exists
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.getWriter().write("{\"loggedIn\": false}");
                return;
            }

            // [PROCESS] Construct Redis session key
            String sessionKey = "session:" + session.getId();

            // [DATA ACCESS] Check session in Redis
            boolean sessionExists = RedisManager.exists(sessionKey, dotenv);
            String loggedStatusFromDb = RedisManager.getField(sessionKey, "logged", dotenv);

            // [BUSINESS RULES] Verify session validity
            if (sessionExists && "true".equals(loggedStatusFromDb)) {
                String emailFromDb = RedisManager.getField(sessionKey, "email", dotenv);
                response.getWriter().write("{\"loggedIn\":true,\"email\":\"" + emailFromDb + "\"}");
                // [SUCCESS LOG] Session valid
                System.out.println("[INFO] [" + LocalDateTime.now() + "] User session valid for email: " + emailFromDb);
            } else {
                response.getWriter().write("{\"loggedIn\": false}");
                // [FAILURE LOG] Session invalid or not logged in
                System.out.println("[INFO] [" + LocalDateTime.now() + "] No active session found or user not logged in");
            }

        } catch (IllegalArgumentException iae) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IAE: " + iae.getMessage());
            request.setAttribute("errorMessage", "Valor inválido detectado: " + iae.getMessage());
            request.getRequestDispatcher("/pages/error/error.jsp").forward(request, response);

        } catch (NullPointerException npe) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NPE: " + npe.getMessage());
            request.setAttribute("errorMessage", "Erro interno: valor nulo encontrado");
            request.getRequestDispatcher("/pages/error/error.jsp").forward(request, response);

        } catch (IOException ioe) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IOE: " + ioe.getMessage());
            request.setAttribute("errorMessage", "Erro de I/O ao verificar sessão");
            request.getRequestDispatcher("/pages/error/error.jsp").forward(request, response);
        }
    }
}
