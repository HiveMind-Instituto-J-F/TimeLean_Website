package hivemind.hivemindweb.Servelts.crud.Company;

import java.io.IOException;
import java.util.List;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/company/delete")
public class Delete extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Get attribute
            String cnpj = req.getParameter("cnpj");
            if(cnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");}

            List<Payment> pendingPayments = null;
            Company company = new Company(cnpj);

            pendingPayments = PaymentDAO.selectPendingPayments(cnpj);

            // Handle case where there are pending payments: if list is non-empty, prevent delete
            if (pendingPayments != null && !pendingPayments.isEmpty()){
                throw new IllegalArgumentException("Company has pending payments and cannot be deleted");
            }

            if (CompanyDAO.switchActive(company, company.isActive())){
                System.out.println("[WARN] Deleted Company.");
                resp.sendRedirect(req.getContextPath() + "\\company\\read");
                return;
            }

            System.err.println("[ERROR] Unknown error.");
            req.setAttribute("errorMessage", "Unable to delete (Unknown)");
            req.getRequestDispatcher("\\html\\crud\\company\\erro\\error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            System.err.println("[ERRO] NullPointerException. , Erro: " + npe.getMessage());
            req.setAttribute("errorMessage", "Unable to delete (Unknown)");
            req.getRequestDispatcher("\\tml/crud\\compan\\error\\error.jsp").forward(req, resp);
        }
        catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("/html/crud/company/create.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
/*
 * BUSINESS RULES (DO NOT DELETE):
 * If the company is set as deactivated, it will be treated the same as a deleted company; this means, it cannot make any operation.
 * Deleted/deactivated companies may be visible depending on the used filter.
 * */
