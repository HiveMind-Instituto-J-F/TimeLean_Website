package hivemind.hivemindweb.Servelts.Company.update;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/company/render-update")
public class Render extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Get and validate parameter
            String cnpj = req.getParameter("cnpj");
            if(cnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");}
            
            // Create and validate company
            Company company;
            try {
                company = CompanyDAO.select(cnpj);
                if (company == null){
                    // Redirect to error.jsp in case of company being null
                    System.err.println("[WARN] ERROR: Company is null");
                    req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // Redirect to error.jsp in case of NullPointerException
                System.err.println("[WARN] ERROR: NullPointerException");
                req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                return;
            }
            
            // Render and dispatch company
            req.setAttribute("company", company);
            req.getRequestDispatcher("/html/crud/company/update.jsp").forward(req, resp);
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
    
