package hivemind.hivemindweb.Servlets.crud.Worker;

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
            // [VALIDATION] Retrieve request parameters with param prefix
            String paramCpf = req.getParameter("cpf");
            String paramName = req.getParameter("name");
            String paramRole = req.getParameter("role");
            String paramSector = req.getParameter("sector");
            String paramLoginEmail = req.getParameter("loginEmail");
            String paramPassword = req.getParameter("loginPassword");

            if (paramCpf == null || paramCpf.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'cpf'");
            if (paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'name'");
            if (paramRole == null || paramRole.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'role'");
            if (paramSector == null || paramSector.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'sector'");
            if (paramLoginEmail == null || paramLoginEmail.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'loginEmail'");
            if (paramPassword == null || paramPassword.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'password'");

            // [PROCESS] Hash password
            String hashedPassword = AuthService.hash(paramPassword);

            // [VALIDATION] Validate session
            HttpSession session = req.getSession(false);
            if (session == null) throw new SessionExpiredException("Session expired");

            // [DATA ACCESS] Retrieve plant identifier from session
            String plantCnpjFromSession = (String) session.getAttribute("plantCnpj");
            if (plantCnpjFromSession == null || plantCnpjFromSession.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'plantCnpj'");

            // [PROCESS] Build worker model preserving business order of fields
            Worker worker = new Worker(paramCpf, paramRole, paramSector, paramName, paramLoginEmail, hashedPassword, plantCnpjFromSession);

            // [DATA ACCESS] Insert worker into database
            boolean inserted = WorkerDAO.insert(worker);
            if (inserted) {
                // [SUCCESS LOG] Log creation and redirect to listing
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Worker created: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
                return;
            } else {
                // [FAILURE LOG] Insertion failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to insert worker: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível criar o trabalhador. Tente novamente mais tarde.");
                req.getRequestDispatcher("/pages/create/worker.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Handle missing or invalid parameters
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Create: IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", "/pages/create/worker.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Unexpected null reference
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Create: NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno. Referência nula detectada.");
            req.setAttribute("errorUrl", "/pages/create/worker.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (SessionExpiredException see) {
            // [FAILURE LOG] Session expired
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Create: SessionExpiredException: " + see.getMessage());
            req.setAttribute("errorMessage", "Sua sessão expirou. Faça login novamente.");
            req.setAttribute("errorUrl", "/pages/workerLogin.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Dispatcher or servlet issues
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Create: ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", "/pages/create/worker.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch all unexpected exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Create: Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/pages/create/worker.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
