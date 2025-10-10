package hivemind.hivemindweb.Servelts;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/insert_company")
public class CreateCompanyServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get Parameters:
        String cnpj = request.getParameter("company-cnpj");
        String name = request.getParameter("company-name");
        String cnae = request.getParameter("company-cnae");
        String registrantCpf = request.getParameter("company-registrant-cpf");
        LocalDate psStartDate = LocalDate.parse(request.getParameter("psubscription-start-date"));
        Integer message;
        Integer planId;


        try{
            planId = PlanDAO.select(request.getParameter("plan-description")).getId();
        } catch (NullPointerException npe){
            planId = null;
        }

        // Create company
        Company company = new Company(cnpj, name, cnae, registrantCpf);

        // try to make inserts by DAO and set messages
        if (planId != null) {
            if (CompanyDAO.insert(company)) {
                if (PlanSubscriptionDAO.insert(new PlanSubscription(psStartDate, cnpj, planId), false)) {
                    message = 1; // success
                } else {
                    CompanyDAO.delete(company); // rollback
                    message = 2; // subscription insert failed
                }
            } else {
                message = 3; // company insert failed
            }
        } else {
            message = 4; // planId null
        }

        // Dispatch request with attribute 'message'
        request.setAttribute("status", message);
        request.getRequestDispatcher("/html/crud/create_company.jsp").forward(request, response);
    }
}
