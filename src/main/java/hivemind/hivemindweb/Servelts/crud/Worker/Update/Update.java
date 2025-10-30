package hivemind.hivemindweb.Servelts.crud.Worker.Update;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/worker/update")
public class Update extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve and validate session and parameters
            HttpSession session = req.getSession();

            String oldCpf = req.getParameter("oldCpf");
            String paramCpf = req.getParameter("cpf");
            String paramName = req.getParameter("name");
            String paramRole = req.getParameter("role");
            String paramSector = req.getParameter("sector");
            String paramLoginEmail = req.getParameter("loginEmail");
            String paramPassword = req.getParameter("loginPassword");
            String paramPlantCnpj = (String) session.getAttribute("plantCnpj");

            if(oldCpf == null || oldCpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'oldCpf'");
            if(paramCpf == null || paramCpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            if(paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            if(paramRole == null || paramRole.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'role'");
            if(paramSector == null || paramSector.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'sector'");
            if(paramLoginEmail == null || paramLoginEmail.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'loginEmail'");
            if(paramPlantCnpj == null || paramPlantCnpj.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");

            // [PROCESS] Hash password, create Worker object and update worker
            String hashedPassword = AuthService.hash(paramPassword);

            Worker workerFromDb = WorkerDAO.selectByCpf(oldCpf);
            workerFromDb.setCpf(paramCpf);
            workerFromDb.setLoginEmail(paramLoginEmail);
            workerFromDb.setName(paramName);
            workerFromDb.setRole(paramRole);
            workerFromDb.setLoginPassword(hashedPassword);
            workerFromDb.setSector(paramSector);

            if(!(paramPassword == null || paramPassword.isEmpty())) workerFromDb.setLoginPassword(paramPassword);

            // [DATA ACCESS] Attempt to update worker in database
            boolean updated = WorkerDAO.update(workerFromDb, oldCpf);
            if(updated) {
                // [SUCCESS LOG] Worker updated successfully
                System.err.println("[INFO] [" + LocalDateTime.now() + "] Worker updated: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Database update failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to update worker in the database: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível atualizar o trabalhador.");
                req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch(IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
