package hivemind.hivemindweb.Servelets.crud.Plan;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/delete")
public class Delete extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate and parse ID parameter
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'id'");
            }

            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve plan by ID from database
            Plan planDb = PlanDAO.selectByID(id);

            // [BUSINESS RULES] Disable plan
            planDb.setActive(false);

            // [PROCESS] Attempt to update plan status
            if (PlanDAO.update(planDb)) {
                // [SUCCESS LOG] Log successful plan deactivation
                System.err.println("[INFO] Plan successfully deactivated: " + planDb.getName());
                req.setAttribute("msg", "Plano " + planDb.getName() + " foi removido com sucesso!");
            } else {
                // [BUSINESS RULES] Plan already inactive
                System.err.println("[INFO] Plan already inactive: " + planDb.getName());
                req.setAttribute("msg", "O plano já está desabilitado.");
            }
            
            Plan planLocal = new Plan(id);
            planLocal.setActive(false);
            
            if(PlanDAO.setActive(planLocal)){
                planLocal.setActive(false);
                System.out.println("[INFO] Plan deactivated successfully!");
                req.setAttribute("msg", "Plano: " + planLocal.getName() + " Foi Removido Com Susseso!");
            }
            else{
                System.out.println("[WARN] Plan is already disabled");
                req.setAttribute("msg", "Plano Ja esta desabilitado");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp); //Error Page
        }
    }
}
