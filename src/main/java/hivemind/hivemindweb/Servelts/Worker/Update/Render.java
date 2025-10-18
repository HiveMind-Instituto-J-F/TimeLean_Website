<<<<<<< HEAD
package hivemind.hivemindweb.Servelts.Worker.Update;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/worker/render-update")
public class Render extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get and validate parameter
        String cpf = request.getParameter("cpf");
        if (cpf == null){
            System.out.println("[WORKER-UPDATE-RENDER] ERROR: cpf is null");
            // Redirect to error.jsp in case of null parameter
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        // Create and validate worker
        Worker worker;
        try {
            worker = WorkerDAO.selectByCpf(cpf);
            if (worker == null){
                // Redirect to error.jsp in case of worker being null
                System.out.println("[WORKER-UPDATE-RENDER] ERROR: Worker is null");
                request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
                return;
            }
        } catch (NullPointerException npe) {
            // Redirect to error.jsp in case of NullPointerException
            System.out.println("[WORKER-UPDATE-RENDER] ERROR: NullPointerException");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        // Render and dispatch worker
        request.setAttribute("worker", worker);
        request.getRequestDispatcher("/html/crud/worker/update.jsp").forward(request, response);
    }
}
=======
package hivemind.hivemindweb.Servelts.Worker.Update;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/worker/render-update")
public class Render extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Get and validate parameter
            String cpf = req.getParameter("cpf");
            if(cpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");}

            // Create and validate worker
            Worker worker;
            worker = WorkerDAO.selectByCpf(cpf);
            if (worker == null){
                // Redirect to error.jsp in case of worker being null
                throw new NullPointerException("Values Is Null, Value: 'cpf'");
            }
            
            // Render and dispatch worker
            req.setAttribute("worker", worker);
            req.getRequestDispatcher("\\html\\crud\\worker\\update.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }
        catch (NullPointerException npe) {
            // Redirect to error.jsp in case of NullPointerException
            System.out.println("[ERROR] Error in Render Company Data, Errro: " + npe.getMessage());
            req.getRequestDispatcher("\\html\\crud\\worker\\error\\error.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
>>>>>>> refs/remotes/origin/dev
