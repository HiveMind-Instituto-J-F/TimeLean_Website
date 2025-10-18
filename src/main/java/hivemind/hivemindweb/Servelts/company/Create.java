package hivemind.hivemindweb.Servelts.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Exception.InvalidForeignKeyException;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/company/create")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Get Parameters:
            String cnpj = req.getParameter("company-cnpj");
            if(cnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");}
            
            String name = req.getParameter("company-name");
            if(name.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'name'");}
            
            String cnae = req.getParameter("company-cnae");
            if(cnae.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnae'");}
            
            String registrantCpf = req.getParameter("company-registrant-cpf");
            if(registrantCpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'registrantCpf'");}
            
            String psStartDateStr = req.getParameter("psubscription-start-date");
            if(psStartDateStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'psStartDate'");}
            LocalDate psStartDate = LocalDate.parse(psStartDateStr);

            Integer message;

            String planIDStr = req.getParameter("plan-description");
            if(planIDStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'planID'");}
            Integer planId = Integer.parseInt(planIDStr);
            
            
            try{
                planId = PlanDAO.select(planIDStr).getId();
            } catch (NullPointerException npe){
                planId = null;
            }
            
            // Create company
            Company company = new Company(cnpj, name, cnae, registrantCpf, true);

            // try to make inserts by DAO and set messages
            if (planId != null) {
                if (CompanyDAO.insert(company)) {
                    if (PlanSubscriptionDAO.insert(new PlanSubscription(psStartDate, cnpj, planId), false)) {
                        message = 1; // success
                    } else {
                        try {
                            CompanyDAO.rollbackCreate(company);  // Rollback
                        } catch (InvalidForeignKeyException ifk) {
                            System.err.println("[ERROR] Invalid FK In Create Company:  " + ifk.getMessage());
                            req.setAttribute("errorMessage", "[WARN]Unable to delete: " + ifk.getMessage());
                            req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                        }
                        message = 2; // subscription insert failed
                    }
                } else {
                    message = 3; // company insert failed
                }
            } else {
                message = 4; // planId null
            }

            // REVER A ADIÇAO DE PLANO

            // Dispatch request with attribute 'message'
            req.setAttribute("status", message);
            req.getRequestDispatcher("/html/crud/company/create.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("/html/crud/company/create.jsp").forward(req, resp);
        }
        catch(DateTimeParseException dpe){
            System.out.println("[ERRO] Failead Convert Date, Erro: " + dpe.getMessage());
            req.setAttribute("error","[ERROR] Ocorreu um erro interno no servidor: " +  dpe.getCause());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + dpe.getMessage());
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