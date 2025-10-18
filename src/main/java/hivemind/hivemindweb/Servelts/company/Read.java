package hivemind.hivemindweb.Servelts.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;

import java.io.IOException;
import java.util.List;

@WebServlet("/company/read")
public class Read extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Company> companies;

        try{
            companies = CompanyDAO.select();

            request.setAttribute("companies", companies);
            request.getRequestDispatcher("/html/crud/company/read.jsp").forward(request, response);
        } catch (NullPointerException npe){
            // Handle null references (e.g., DAO returned null unexpectedly)
            System.err.println("[WORKER-READ] Null reference encountered: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while retrieving plant or worker data.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        }
    }
}
