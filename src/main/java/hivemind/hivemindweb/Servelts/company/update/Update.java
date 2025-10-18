<<<<<<< HEAD
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Receive form parameters
            String cnpj = request.getParameter("cnpj");
            String name = request.getParameter("name");
            String cnae = request.getParameter("cnae");
            String registrantCpf = request.getParameter("registrantCpf");

            // Validate required parameters
            if (cnpj == null || name == null || cnae == null || registrantCpf == null) {
                request.setAttribute("error", "All fields are required.");
                request.getRequestDispatcher("/html/crud/company/update.jsp").forward(request, response);
                return;
            }

            // Create a Company object with the updated data
            Company company = new Company(cnpj, name, cnae, registrantCpf);
            System.out.println(company);

            // Update the company in the database via DAO
            boolean updated = CompanyDAO.update(company);

            if (updated) {
                // Redirect to list page if update succeeds
                response.sendRedirect(request.getContextPath() + "/company/read");
            } else {
                // Forward back to update page with error message if update fails
                request.setAttribute("company", company);
                request.setAttribute("error", "Failed to update the company.");
                request.getRequestDispatcher("/html/crud/company/update.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Catch any unexpected exception and forward to an error page
            System.err.println("[COMPANY-UPDATE] ERROR: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred while updating the company.");
            request.getRequestDispatcher("/html/crud/company/update.jsp").forward(request, response);
        }
    }
=======
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
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
}