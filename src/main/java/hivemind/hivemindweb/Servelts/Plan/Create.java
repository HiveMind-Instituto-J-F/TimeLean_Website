<<<<<<< HEAD
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

@WebServlet("/create-plan")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
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
            
            Plan planLocal = new Plan(name, description, duration, price);
            System.out.println(planLocal);
            if(PlanDAO.insert(planLocal,false)){
                System.out.println("[WARN] Insert Plan Sussefly");
                req.setAttribute("msg", "Plan Foi Adicionado Com Susseso!");
                req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
            }
            else{
                System.out.println("[WARN] Erro in PlanDAO");
                System.out.println("[ERROR] Plan Nao foi Adicionado devido a um Erro!");
                req.setAttribute("msg", "Plan Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException se){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
        }catch(InvalidForeignKeyException ifk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            // resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  ifk.getCause());
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
        }
    }
}
=======
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

@WebServlet("/create-plan")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
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
            System.out.println(nameDB);

            if(nameDB.equalsIgnoreCase(name)){
                throw new InvalidForeignKeyException("Name already exists in the database");
            }
            
            Plan planLocal = new Plan(name, description, duration, price);
            System.out.println(planLocal);
            if(PlanDAO.insert(planLocal,false)){
                System.out.println("[WARN] Insert Plan Sussefly");
                req.setAttribute("msg", "Plan Foi Adicionado Com Susseso!");
                req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
            }
            else{
                System.out.println("[WARN] Erro in PlanDAO");
                System.out.println("[ERROR] Plan Nao foi Adicionado devido a um Erro!");
                System.out.println("[ERROR]:" + nameDB + " " +  planLocal);
                req.setAttribute("Errro", "Plan Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch(IllegalArgumentException se){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
        }catch(InvalidForeignKeyException ifk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            // resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Valor Nao Pode Ser encotrado No banco: " +  ifk.getCause());
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
        }
    }
}
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
