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

@WebServlet("/delete-plan")
public class Delete extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String idStr = req.getParameter("id");
            if(idStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id'");}
            int id = Integer.parseInt(idStr);

            int idDB = PlanDAO.getID(id);
            String name = PlanDAO.getName(id);

            if(!(idDB == id)){
                throw new InvalidForeignKeyException("ID not exit in DB");
            }
            
            Plan planLocal = new Plan(id,name);
            planLocal.setActive(false);
            
            if(PlanDAO.setActive(planLocal)){
                planLocal.setActive(false);
                System.out.println("[INF] Plan Desativado Com susseso!");
                req.setAttribute("msg", "Plano: " + planLocal.getName() + " Foi Removido Com Susseso!");
            }
            else{
                System.out.println("[WARN] Plano Ja esta desabilitado");
                req.setAttribute("msg", "Plano Ja esta desabilitado");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
        }catch(InvalidForeignKeyException ifk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            // resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  ifk.getCause());
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp); //Error Page
        }
    }
}
