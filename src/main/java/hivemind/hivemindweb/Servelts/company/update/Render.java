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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get and validate parameter
        String cnpj = request.getParameter("cnpj");
        if (cnpj == null){
            // Redirect to error.jsp in case of null parameter
            System.out.println("[COMPANY-UPDATE-RENDER] ERROR: cnpj is null");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
            return;
        }

        // Create and validate company
        Company company;
        try {
            company = CompanyDAO.select(cnpj);
            if (company == null){
                // Redirect to error.jsp in case of company being null
                System.err.println("[COMPANY-UPDATE-RENDER] ERROR: Company is null");
                request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
                return;
            }
        } catch (NullPointerException npe) {
            // Redirect to error.jsp in case of NullPointerException
            System.err.println("[COMPANY-UPDATE-RENDER] ERROR: NullPointerException");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
            return;
        }

        // Render and dispatch company
        request.setAttribute("company", company);
        request.getRequestDispatcher("/html/crud/company/update.jsp").forward(request, response);
    }
}
