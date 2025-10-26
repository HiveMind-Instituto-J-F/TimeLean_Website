package hivemind.hivemindweb.Servelts.crud.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // [PROCESS] Retrieve and validate 'status' parameter
            String paramStatus = req.getParameter("status");
            if (paramStatus == null || paramStatus.isEmpty()) {
                paramStatus = null;
            }

            FilterType.Company filter = FilterType.Company.ALL_VALUES; // Default filter
            if (paramStatus != null) {
                switch (paramStatus.toLowerCase()) {
                    case "active-companies":
                        filter = FilterType.Company.ACTIVE;
                        break;
                    case "inactive-companies":
                        filter = FilterType.Company.INACTIVE;
                        break;
                    case "companies-with-pending-payments":
                        filter = FilterType.Company.WITH_PENDING_PAYMENT;
                        break;
                    case "all-companies":
                        filter = FilterType.Company.ALL_VALUES;
                        break;
                    default:
                        // [FAILURE LOG] Invalid filter
                        System.err.println("[ERROR] Invalid filter: " + paramStatus);
                        req.setAttribute("errorMessage", "Filtro inválido.");
                        req.setAttribute("errorUrl", "/html/toUser.html");
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                }
            }

            // [DATA ACCESS] Retrieve companies based on filter
            List<Company> companies = CompanyDAO.selectFilter(filter);

            // [PROCESS] Forward companies to JSP
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/html/crud/company/read.jsp").forward(req, resp);

            // [SUCCESS LOG] Successfully retrieved companies
            System.err.println("[INFO] Companies retrieved successfully. Filter: " + filter);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Illegal argument encountered
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + iae.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}