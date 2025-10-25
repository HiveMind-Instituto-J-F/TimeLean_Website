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
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Company/update/Render.java
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
                    System.err.println("[WARN]Company is null");
                    req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // Redirect to error.jsp in case of NullPointerException
                System.err.println("[ERROR] NullPointerException");
                req.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(req, resp);
=======
        try {
            // [VALIDATION] Get and validate parameter
            String paramCnpj = req.getParameter("cnpj");
            if (paramCnpj == null || paramCnpj.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");
            }

            // [DATA ACCESS] Retrieve company from database
            Company company = CompanyDAO.select(paramCnpj);
            if (company == null) {
                // [FAILURE LOG] Company not found
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Company not found for CNPJ: " + paramCnpj);
                req.setAttribute("errorMessage", "Empresa não encontrada.");
                req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Company/update/Render.java
                return;
            }

            // [PROCESS] Set company attribute and forward to update page
            req.setAttribute("company", company);
            req.getRequestDispatcher("/html/crud/company/update.jsp").forward(req, resp);

            // [SUCCESS LOG] Company render successful
            System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Company render successful for CNPJ: " + paramCnpj);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid parameter
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: referência nula encontrada.");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
