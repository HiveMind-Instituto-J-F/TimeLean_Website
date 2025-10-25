package hivemind.hivemindweb.Servelets.crud.Company.update;

import java.io.IOException;
import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@WebServlet("/company/render-update")
public class Render extends HttpServlet {
    @Override
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
                    System.err.println("[WARN] Company is null");
                    req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // Redirect to error.jsp in case of NullPointerException
                System.err.println("[ERROR] NullPointerException");
                req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Set company attribute and forward to update page
            req.setAttribute("company", company);
            req.getRequestDispatcher("/html/crud/company/update.jsp").forward(req, resp);

            // [SUCCESS LOG] Company render successful
            System.err.println("[INFO] [" + LocalDateTime.now() + "] Company render successful for CNPJ: " + cnpj);

        } catch (IllegalArgumentException iae) {
            // [ERROR] Invalid parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [ERROR] Null reference encountered
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: referência nula encontrada.");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [ERROR] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [ERROR] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
