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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // [VALIDATION] Check for existing session
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new IllegalArgumentException("Sessão não encontrada");
            }

            // [DATA ACCESS] Remove session from Redis
            String sessionKey = "session:" + session.getId();
            RedisManager.delete(sessionKey, dotenv);

            // [PROCESS] Invalidate session
            session.invalidate();

            // [SUCCESS LOG] Logout successful
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Session successfully terminated");

            // [PROCESS] Redirect to login page
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");

        } catch (IllegalArgumentException iae) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IAE: " + iae.getMessage());
            request.setAttribute("errorMessage", iae.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);

        } catch (NullPointerException npe) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NPE: " + npe.getMessage());
            request.setAttribute("errorMessage", "Erro interno: valor nulo encontrado");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);

        } catch (IOException ioe) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IOE: " + ioe.getMessage());
            request.setAttribute("errorMessage", "Erro de I/O ao processar logout");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        RedisManager.close();
        super.destroy();
    }
}
