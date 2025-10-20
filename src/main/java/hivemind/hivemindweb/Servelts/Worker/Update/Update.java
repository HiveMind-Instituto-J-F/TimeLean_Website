package hivemind.hivemindweb.Servelts.Worker.Update;

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

@WebServlet("/worker/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Retrieve the current HTTP session
            HttpSession session = req.getSession();
            
            // Get worker data from form submission
            String cpf = req.getParameter("cpf");
            if(cpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");}
            
            String name = req.getParameter("name");
            if(name.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'name'");}
            
            String role = req.getParameter("role");
            if(role.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'role'");}
            
            String sector = req.getParameter("sector");
            if(sector.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'sector'");}
            
            String loginEmail = req.getParameter("loginEmail");
            if(sector.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'sector'");}
            
            String passwordLocal = req.getParameter("loginPassword");
            if(passwordLocal.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'password'");}
            String loginPassword = AuthService.hash(passwordLocal);

            String plantCnpj = (String) session.getAttribute("plantCnpj");
            if(plantCnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");}

            Worker worker = null;

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
                    System.err.println("[WORKER-UPDATE] Failed to update worker in the database.");
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
        }
    }
}
