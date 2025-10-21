package hivemind.hivemindweb.Servelts.PlanSubscription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Exception.InvalidPrimaryKeyException;
import hivemind.hivemindweb.models.PlanSubscription;

@WebServlet("/planSub/delete")
public class Delete extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String idStr = req.getParameter("id");
            if(idStr == null || idStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id'");}
            int id = Integer.parseInt(idStr); 

            PlanSubscription planSubscriptionLocal = new PlanSubscription(id);
            int DBid = PlanSubscriptionDAO.getID(planSubscriptionLocal);
            if(!(DBid == id)){throw new InvalidPrimaryKeyException("Primary Key is not valid");}
            if(PlanSubscriptionDAO.delete(planSubscriptionLocal)){
                System.out.println("[WARN] Delete PlanSubscription Sussefly");
                req.setAttribute("msg", "PlanSubscription Foi Removido Com Susseso!");
            }
            else{
                System.out.println("[WARN] Erro in PlanSubscriptionDAO");
                System.out.println("[ERROR] Plan Subscription Nao foi Adicionado devido a um Erro!");
                req.setAttribute("msg", "Plan Subscription Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);
        }catch(IllegalArgumentException se){
            System.out.println("[ERROR] Error In Delete Servelet, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);
        }
        catch(InvalidPrimaryKeyException ipk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ipk.getCause() + " Erro: " + ipk.getMessage() + ")");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ipk.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  ipk.getCause());
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);
        }
        catch(DateTimeParseException dpe){
            System.out.println("[ERRO] Failead Convert Date, Erro: " + dpe.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  dpe.getCause());
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);
        }
    }
}
