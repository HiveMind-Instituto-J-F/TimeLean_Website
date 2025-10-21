package hivemind.hivemindweb.Servelts.PlanSubscription.update;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/planSub/render-update")
public class Render extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Get and validate parameter
            String idStr = req.getParameter("id");
            if(idStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id'");}
            int id = Integer.parseInt(idStr);
            
            // Create and validate planSubscription
            PlanSubscription planSubLocal;
            try {
                planSubLocal = PlanSubscriptionDAO.getPlanSubById(id);
                if (planSubLocal == null){
                    // Redirect to error.jsp in case of planSubscription being null
                    System.err.println("[WARN] ERROR: PlanSubscription is null");
                    req.getRequestDispatcher("/html/crud/planSub/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // Redirect to error.jsp in case of NullPointerException
                System.err.println("[WARN] ERROR: NullPointerException");
                req.getRequestDispatcher("/html/crud/planSub/error/error.jsp").forward(req, resp);
                return;
            }
            
            // Render and dispatch planSubscription
            req.setAttribute("planSub", planSubLocal);
            req.getRequestDispatcher("/html/crud/planSub/update.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
    
