package hivemind.hivemindweb.Servelts.PlanSubscription;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Exception.InvalidForeignKeyException;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-plan-subcription")
public class Update extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String id_planStr = req.getParameter("id_plan");
            if(id_planStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id_plan'");}
            int id_plan = Integer.parseInt(id_planStr);

            String startDateStr = req.getParameter("start_date");
            if(startDateStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'startDate'");}
            LocalDate startDate = LocalDate.parse(startDateStr); 

            String cnpjCompany = req.getParameter("cnpj_company");
            if(cnpjCompany.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnjp_company'");}

            String cnpjFromDB = CompanyDAO.getCNPJ(cnpjCompany);
            if (cnpjFromDB == null || !cnpjFromDB.equalsIgnoreCase(cnpjCompany)) {
                throw new InvalidForeignKeyException("Foreign Key is not valid");
            }
            
            PlanSubscription planSubscriptionLocal = new PlanSubscription(startDate, cnpjCompany, id_plan);
            if(PlanSubscriptionDAO.update(planSubscriptionLocal)){
                System.out.println("[WARN] Update PlanSubscription Sussefly");
                req.setAttribute("msg", "PlanSubscription Foi Atalizado Com Susseso!");
            }
            else{
                System.out.println("[WARN] Erro in PlanSubscriptionDAO");
                System.out.println("[ERROR] Plan Subscription Nao foi Adicionado devido a um Erro!");
                req.setAttribute("msg", "Plan Subscription Nao Pode Ser encotrado!");
            }
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);;
        }catch(IllegalArgumentException se){
            System.out.println("[ERROR] Error In Update Servelet, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
        }
        catch(InvalidForeignKeyException ifk){
            System.out.println("[ERROR] Foreign Key is not valid, Erro: (Cause: " + ifk.getCause() + " Erro: " + ifk.getMessage() + ")");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Value: " + ifk.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  ifk.getCause());
        }
        catch(DateTimeParseException dpe){
            System.out.println("[ERRO] Failead Convert Date, Erro: " + dpe.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  dpe.getCause());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
