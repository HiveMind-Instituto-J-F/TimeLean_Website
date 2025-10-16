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
            Company company = new Company(cnpj, name, cnae, registrantCpf, true);
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
            request.setAttribute("error", "An unexpected error occurred while updating the company.");
            request.getRequestDispatcher("/html/crud/company/update.jsp").forward(request, response);
        }
    }
}