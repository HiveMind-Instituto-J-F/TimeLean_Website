package hivemind.hivemindweb.Servelts.PlanSubscription;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create-plan-subcription")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String startDateStr = req.getParameter("start_date");
            if(startDateStr.isEmpty()){throw new ServletException("Values Is Null, Value: 'startDate'");}
            LocalDate startDate = LocalDate.parse(startDateStr); 

            String cnpjCompanyStr = req.getParameter("cnpj_company");
            if(cnpjCompanyStr.isEmpty()){throw new ServletException("Values Is Null, Value: 'cnjp_company'");}
            int cnpj_company = Integer.getInteger(cnpjCompanyStr);

            String id_planStr = req.getParameter("id_plan");
            if(id_planStr.isEmpty()){throw new ServletException("Values Is Null, Value: 'id_plan'");}
            int id_plan = Integer.parseInt(id_planStr);
            
            PlanSubscription planSubscriptionLocal = new PlanSubscription(startDate, cnpjCompanyStr, id_plan);
            if(PlanSubscriptionDAO.insert(planSubscriptionLocal,false)){
                System.out.println("[WARN] Insert PlanSubscription Sussefly");
                req.setAttribute("msg", "PlanSubscription Foi Adicionado Com Susseso!");
            }
            else{
                System.out.println("[WARN] Erro in PlanSubscriptionDAO");
                System.out.println("[ERROR] Plan Subscription Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("\\html\\crud\\planSub.jsp");
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", se.getMessage());
        }catch(IllegalArgumentException ime){
            System.out.println("[ERROR] Invaliad Input, Erro: " + ime.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + ime.getMessage());
            req.setAttribute("error", ime.getMessage());
        }
        catch(DateTimeParseException dpe){
            System.out.println("[ERRO] Failead Convert Date, Erro: " + dpe.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("error", dpe.getCause());
        }
    }
}
