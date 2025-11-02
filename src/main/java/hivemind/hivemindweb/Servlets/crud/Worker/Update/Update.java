package hivemind.hivemindweb.Servlets.crud.Worker.Update;

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

            String cpfParam = req.getParameter("cpf");
            String nameParam = req.getParameter("name");
            String roleParam = req.getParameter("role");
            String sectorParam = req.getParameter("sector");
            String loginEmailParam = req.getParameter("loginEmail");
            String loginPasswordParam = req.getParameter("loginPassword");
            String plantCnpjParam = (String) session.getAttribute("plantCnpj");

            if(cpfParam == null || cpfParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            if(!cpfParam.matches("^[0-9]{11}$")) throw new IllegalArgumentException("Invalid CPF format: " + cpfParam);
            if(nameParam == null || nameParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            if(roleParam == null || roleParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'role'");
            if(sectorParam == null || sectorParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'sector'");
            if(loginEmailParam == null || loginEmailParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'loginEmail'");
            if(plantCnpjParam == null || plantCnpjParam.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");

            // [DATA ACCESS] Hash password, create Worker object and update worker
            String hashedPassword = AuthService.hash(loginPasswordParam);

            Worker workerFromDb = WorkerDAO.selectByCpf(cpfParam);
            if(workerFromDb == null) throw new NullPointerException("Worker not found for CPF: " + cpfParam);

            workerFromDb.setCpf(cpfParam);
            workerFromDb.setLoginEmail(loginEmailParam);
            workerFromDb.setName(nameParam);
            workerFromDb.setRole(roleParam);
            workerFromDb.setSector(sectorParam);

            if(!loginPasswordParam.isEmpty()) workerFromDb.setLoginPassword(hashedPassword);

            // [DATA ACCESS] Attempt to update worker in database
            boolean updated = WorkerDAO.update(workerFromDb);
            if(updated) {
                // [SUCCESS LOG] Worker updated successfully
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Worker updated: " + cpfParam);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Database update failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to update worker in the database: " + cpfParam);
                req.setAttribute("errorMessage", "Não foi possível atualizar o trabalhador.");
                req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
            }

        } catch(IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch(NullPointerException npe) {
            // [FAILURE LOG] Worker not found
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Trabalhador não encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch(ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch(Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
