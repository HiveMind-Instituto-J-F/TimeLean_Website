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

            String cpf = req.getParameter("cpf");
            String name = req.getParameter("name");
            String role = req.getParameter("role");
            String sector = req.getParameter("sector");
            String loginEmail = req.getParameter("loginEmail");
            String loginPassword = req.getParameter("loginPassword");
            String plantCnpj = (String) session.getAttribute("plantCnpj");

            if(cpf == null || cpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            if(name == null || name.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            if(role == null || role.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'role'");
            if(sector == null || sector.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'sector'");
            if(loginEmail == null || loginEmail.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'loginEmail'");
            if(plantCnpj == null || plantCnpj.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");

            // [PROCESS] Hash password, create Worker object and update worker
            String hashedPassword = AuthService.hash(loginPassword);

            Worker workerFromDb = WorkerDAO.selectByCpf(cpf);
            if(workerFromDb == null) throw new NullPointerException("Worker not found for CPF: " + cpf);

            workerFromDb.setCpf(cpf);
            workerFromDb.setLoginEmail(loginEmail);
            workerFromDb.setName(name);
            workerFromDb.setRole(role);
            workerFromDb.setSector(sector);

            if(!loginPassword.isEmpty()) workerFromDb.setLoginPassword(hashedPassword);

            // [DATA ACCESS] Attempt to update worker in database
            boolean updated = WorkerDAO.update(workerFromDb);
            if(updated) {
                // [SUCCESS LOG] Worker updated successfully
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Worker updated: " + cpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Database update failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to update worker in the database: " + cpf);
                req.setAttribute("errorMessage", "Não foi possível atualizar o trabalhador.");
                req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch(IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(NullPointerException npe) {
            // [FAILURE LOG] Worker not found
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Trabalhador não encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
