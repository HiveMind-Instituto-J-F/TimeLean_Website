package hivemind.hivemindweb.Servelts.Plan;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.Exception.InvalidForeignKeyException;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/active")
public class Active extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String idStr = req.getParameter("duration");
            if(idStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id'");}
            int id = Integer.parseInt(idStr);

            int idDB = PlanDAO.getID(id);

            if(id == idDB){throw new InvalidForeignKeyException("ID not exists in the database");}

            Plan planLocal = new Plan(idDB,true);
            if(PlanDAO.getAction(idDB)){
                PlanDAO.setActive(planLocal);
                System.out.println("[WARN] Insert Plan Sussefly");
                req.setAttribute("msg", "Plan Foi Adicionado Com Susseso!");
                req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
            }
            else{
                System.err.println("[WARN] Erro in PlanDAO");
                System.err.println("[ERROR] Plan Nao foi Ativo devido a um Erro!");
                req.setAttribute("Errro", "Plan Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException se){
            System.err.println("[ERROR] Error In Create Servelet, Error: "+ se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
        }catch(InvalidForeignKeyException ifk){
            System.err.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            // resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Valor Nao Pode Ser encotrado No banco: " +  ifk.getCause());
        }
        catch(ServletException se){
            System.err.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
        }
    }
}
