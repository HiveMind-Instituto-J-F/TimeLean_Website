package hivemind.hivemindweb.Servelts.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/company/read")
public class Read extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Company> companies;
        String status = request.getParameter("status");

        String filter = "active-companies"; // Default filter
        if (status != null) {
            if (status.equalsIgnoreCase("active-companies")) {
                filter = "active-companies";
            } else if (status.equalsIgnoreCase("inactive-companies")) {
                filter = "inactive-companies";
            } else if (status.equalsIgnoreCase("companies-with-pending-payments")){
                filter = "companies-with-pending-payments";
            } else if (status.equalsIgnoreCase("all-companies")){
                filter = "all-companies";
            }
            else {
                System.err.println("[COMPANY-DELETE] Invalid filter.");
                request.setAttribute("errorMessage", "Invalid filter.");
                request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
                return;
            }
        }

        companies = CompanyDAO.selectFilter(filter);

        request.setAttribute("companies", companies);
        request.getRequestDispatcher("/html/crud/company/read.jsp").forward(request, response);
    }
}

/*
* BUSINESS RULES (DO NOT DELETE):
* If the company is set as deactivated, it will be treated the same as a deleted company; this means, it cannot make any operation.
* Deleted/deactivated companies may be visible depending on the used filter.
* */