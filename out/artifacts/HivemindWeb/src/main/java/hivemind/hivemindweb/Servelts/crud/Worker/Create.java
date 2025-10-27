package hivemind.hivemindweb.Servelts.crud.Worker;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.Exception.SessionExpiredException;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/worker/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Get and validate form parameters
            String paramCpf = req.getParameter("cpf");
            String paramName = req.getParameter("name");
            String paramRole = req.getParameter("role");
            String paramSector = req.getParameter("sector");
            String paramLoginEmail = req.getParameter("loginEmail");
            String paramPassword = req.getParameter("loginPassword");

            if(paramCpf == null || paramCpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            if(paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            if(paramRole == null || paramRole.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'role'");
            if(paramSector == null || paramSector.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'sector'");
            if(paramLoginEmail == null || paramLoginEmail.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'loginEmail'");
            if(paramPassword == null || paramPassword.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'password'");

            // [PROCESS] Hash the password
            String hashedPassword = AuthService.hash(paramPassword);

            // [VALIDATION] Retrieve the session
            HttpSession session = req.getSession(false);
            if(session == null) throw new SessionExpiredException("Session expired. Please log in again.");

            String paramPlantCnpj = (String) session.getAttribute("plantCnpj");

            // [PROCESS] Create Worker object
            Worker worker = new Worker(paramCpf, paramRole, paramSector, paramName, paramLoginEmail, hashedPassword, paramPlantCnpj);

            // [DATA ACCESS] Insert Worker into the database
            if(WorkerDAO.insert(worker)) {
                // [SUCCESS LOG] Worker created successfully
                System.err.println("[INFO] [" + LocalDateTime.now() + "] Worker created: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Database insertion failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to insert worker: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível criar o trabalhador. Tente novamente mais tarde.");
                req.setAttribute("errorUrl", "/html/crud/worker/create.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch(IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", "/html/crud/worker/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(SessionExpiredException see) {
            // [FAILURE LOG] Session expired
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] SessionExpiredException: " + see.getMessage());
            req.setAttribute("errorMessage", "Sua sessão expirou. Faça login novamente.");
            req.setAttribute("errorUrl", "/html/login.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/crud/worker/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/worker/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
