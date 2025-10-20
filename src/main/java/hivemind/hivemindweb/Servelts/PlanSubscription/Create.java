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

@WebServlet("/create-plan-subcription")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String startDateStr = req.getParameter("start_date");
            if(startDateStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'startDate'");}
            LocalDate startDate = LocalDate.parse(startDateStr); 

            String cnpjCompany = req.getParameter("cnpj_company");
            if(cnpjCompany.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnjp_company'");}

            String cnpjFromDB = CompanyDAO.getCNPJ(cnpjCompany);
            if (cnpjFromDB == null || !cnpjFromDB.equalsIgnoreCase(cnpjCompany)) {
                throw new InvalidForeignKeyException("Foreign Key is not valid");
            }

            String idPlanStr = req.getParameter("id_plan");
            if(idPlanStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'id_plan'");}
            int idpPlan = Integer.parseInt(idPlanStr);

            String numberInstallmentsStr = req.getParameter("number_installments");
            if(numberInstallmentsStr == null || numberInstallmentsStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'number_installments'");}
            int numberInstallments = Integer.parseInt(numberInstallmentsStr);

            PlanSubscription planSubscriptionLocal = new PlanSubscription(startDate, cnpjCompany, idpPlan, numberInstallments);
            if(PlanSubscriptionDAO.insert(planSubscriptionLocal,false)){
                System.out.println("[WARN] Insert PlanSubscription Sussefly");
                req.setAttribute("msg", "PlanSubscription Foi Adicionado Com Susseso!");
            }
            else{
                System.out.println("[WARN] Erro in PlanSubscriptionDAO");
                System.out.println("[ERROR] Plan Subscription Nao foi Adicionado devido a um Erro!");
                req.setAttribute("msg", "Plan Subscription Nao foi Adicionado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\planSub.jsp").forward(req, resp);;
        }catch(IllegalArgumentException se){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ se.getMessage());
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
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
        }
    }
}
