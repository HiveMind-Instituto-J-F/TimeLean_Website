package hivemind.hivemindweb.Servelts.Company.update;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/company/update")
public class Update extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Receive form parameters
            String cnpj = req.getParameter("company-cnpj");
            if(cnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");}
            
            String name = req.getParameter("company-name");
            if(name.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'name'");}
            
            String cnae = req.getParameter("company-cnae");
            if(cnae.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnae'");}
            
            String registrantCpf = req.getParameter("company-registrant-cpf");
            if(registrantCpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'registrantCpf'");}

            // Create a Company object with the updated data
            Company company = new Company(cnpj, name, cnae, registrantCpf, true);
    
            // Update the company in the database via DAO
            if (CompanyDAO.update(company)) {
                // Redirect to list page if update succeeds
                System.out.println("[WARN] Update Company Sussefly");
                req.setAttribute("msg", "Company Foi Atalizado Com Susseso!");
                resp.sendRedirect(req.getContextPath() + "/company/read");
            } else {
                // Forward back to update page with error message if update fails
                req.setAttribute("company", company);
                req.setAttribute("error", "Failed to update the company.");
                req.getRequestDispatcher("/html/crud/company/update.jsp").forward(req, resp);
            }

        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}