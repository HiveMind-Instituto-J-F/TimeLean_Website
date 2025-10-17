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

@WebServlet("/update-plan")
public class Update extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String intStr = req.getParameter("id");
            if(intStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id'");}
            int id = Integer.parseInt(intStr);

            String durationStr = req.getParameter("duration");
            if(durationStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'duration'");}
            int duration = Integer.parseInt(durationStr);

            String description = req.getParameter("description");
            if(description.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'description'");}

            String priceStr = req.getParameter("price");
            if(priceStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'price'");}
            double price = Double.parseDouble(priceStr);

            String name = req.getParameter("name");
            if(name.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'nameStr'");}
            
            String nameDB = PlanDAO.getName(name);

            if(!(nameDB.equalsIgnoreCase(name))){
                throw new InvalidForeignKeyException("Name already exists in the database");
            }

            int idDB = PlanDAO.getID(id);

            if(!(id == idDB)){
                throw new InvalidForeignKeyException("ID already exists in the database");
            }
            
            Plan planLocal = new Plan(id,name, description, duration, price);
            
            if(PlanDAO.update(planLocal)){
                System.out.println("[WARN] Insert Plan Sussefly");
                req.setAttribute("msg", "Plano Foi Atalizado Com Susseso!");
                req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
            }
            else{
                System.out.println("[WARN] Erro in PlanDAO");
                System.out.println("[ERROR] Plan Nao foi Adicionado devido a um Erro!");
                req.setAttribute("msg", "Plan Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(InvalidForeignKeyException ifk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            // resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  ifk.getCause());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
