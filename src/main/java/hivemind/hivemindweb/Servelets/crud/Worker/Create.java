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

@WebServlet("/worker/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Retrieve worker data from the form
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
            
            // Get the current session (does not create a new one if absent)
            HttpSession session = req.getSession(false);
            
            if (session == null) {
                // Handle invalid or expired session
                throw new SessionExpiredException("Session expired. Please log in again.");
            }
            String plantCnpj = (String) session.getAttribute("plantCnpj");
            
            Worker worker;
            // Attempt to create a Worker object with validated data
            worker = new Worker(
                cpf,
                role,
                sector,
                name,
                loginEmail,
                loginPassword,
                plantCnpj
            );
                
            // Try to insert the worker into the database                
            if (WorkerDAO.insert(worker)) {
                // Redirect to list page if insertion is successful
                resp.sendRedirect(req.getContextPath() + "\\worker\\read");
            } else {
                // Handle insertion failure (e.g., duplicate CPF)
                System.err.println("[ERROR] Failed to insert worker in the database.");
                req.setAttribute("errorMessage", "Unable to create worker. Please try again later.");
                req.getRequestDispatcher("\\html\\crud\\worker\\error\\error.jsp").forward(req, resp);
            }
        }catch(IllegalArgumentException ia){
            System.err.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(SessionExpiredException see){
            System.err.println("[ERROR] Session Expirar, Error: "+ see.getMessage());
            req.setAttribute("errorMessage", "Session expired. Please log in again.");
            req.getRequestDispatcher("\\html\\login.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.err.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
    
