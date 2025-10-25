package hivemind.hivemindweb.Servelets.crud.Worker.Update;

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

            String paramCpf = req.getParameter("cpf");
            String paramName = req.getParameter("name");
            String paramRole = req.getParameter("role");
            String paramSector = req.getParameter("sector");
            String paramLoginEmail = req.getParameter("loginEmail");
            String paramPassword = req.getParameter("loginPassword");
            String paramPlantCnpj = (String) session.getAttribute("plantCnpj");

            if(paramCpf == null || paramCpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            if(paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            if(paramRole == null || paramRole.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'role'");
            if(paramSector == null || paramSector.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'sector'");
            if(paramLoginEmail == null || paramLoginEmail.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'loginEmail'");
            if(paramPassword == null || paramPassword.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'password'");
            if(paramPlantCnpj == null || paramPlantCnpj.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");

<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Worker/Update/Update.java
            // Attempt to create a Worker object with the provided data
            worker = new Worker(
                cpf,
                role,
                sector,
                name,
                loginEmail,
                loginPassword,
                plantCnpj
                );
                
                // Attempt to update worker information in the database
                boolean updated = WorkerDAO.update(worker);
                
                if (updated) {
                    // Redirect to the worker list if the update succeeds
                    resp.sendRedirect(req.getContextPath() + "/worker/read");
                } else {
                    // Handle database update failure
                    System.err.println("[ERROR] Failed to update worker in the database.");
                    req.setAttribute("errorMessage", "Failed to update the worker.");
                    req.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(req, resp);
                }
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
=======
            // [PROCESS] Hash password and create Worker object
            String hashedPassword = AuthService.hash(paramPassword);
            Worker worker = new Worker(paramCpf, paramRole, paramSector, paramName, paramLoginEmail, hashedPassword, paramPlantCnpj);

            // [DATA ACCESS] Attempt to update worker in database
            boolean updated = WorkerDAO.update(worker);
            if(updated) {
                // [SUCCESS LOG] Worker updated successfully
                System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Worker updated: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Database update failed
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Failed to update worker in the database: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível atualizar o trabalhador.");
                req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch(IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch(Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worke/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Worker/Update/Update.java
        }
    }
}
