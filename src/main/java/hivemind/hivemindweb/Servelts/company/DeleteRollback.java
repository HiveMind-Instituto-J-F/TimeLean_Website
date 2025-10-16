package hivemind.hivemindweb.Servelts.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/company/delete/rollback")
public class DeleteRollback extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get attributes
        String cnpj = request.getParameter("cnpj");

        if (cnpj == null){
            System.err.println("[COMPANY-DELETE] Unknown error.");
            request.setAttribute("errorMessage", "Unable to delete (Unknown)");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        }


        Company company = new Company(cnpj);
        try {
            if (CompanyDAO.switchActive(company, true)){
                System.out.println("[COMPANY-DELETE] Deleted Company.");
                response.sendRedirect(request.getContextPath() + "/company/read");
                return;
            }

            System.err.println("[COMPANY-DELETE] Unknown error.");
            request.setAttribute("errorMessage", "Unable to delete (Unknown)");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        } catch (NullPointerException npe){
            System.err.println("[COMPANY-DELETE] NullPointerException exception.");
            request.setAttribute("errorMessage", "Unable to delete: NullPointerException");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        }
    }
}
