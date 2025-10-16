package hivemind.hivemindweb.Servelts.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.Exception.ForeignKeyViolationException;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/company/delete")
public class Delete extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get attribute
        System.out.println("TRYING TO DELETE");
        String cnpj = request.getParameter("cnpj");

        if (cnpj == null){
            System.err.println("[COMPANY-DELETE] Null CNPJ.");
            request.setAttribute("errorMessage", "Unable to delete: Null CNPJ)");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
        }

        List<Payment> pendingPayments = null;
        Company company = new Company(cnpj);
        try{
            pendingPayments = PaymentDAO.selectPendingPayments(cnpj);
            if (pendingPayments == null){
                System.err.println("[COMPANY-DELETE] Null Pending Payments.");
                request.setAttribute("errorMessage", "Unable to delete: Null Pending Payments");
                request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
                return;
            }
        } catch (NullPointerException npe){
            System.err.println("[COMPANY-DELETE] NullPointerException.");
            request.setAttribute("errorMessage", "Unable to delete (Unknown)");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
            return;
        }

        // Handle case where there are pending payments
        if (!pendingPayments.isEmpty()){
            System.err.println("[COMPANY-DELETE] Pending Payments.");
            request.setAttribute("errorMessage", "Unable to delete: There are pending payments.");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
            return;
        }

        try {
            if (CompanyDAO.delete(company)){
                System.err.println("[COMPANY-DELETE] Deleted Company.");
                response.sendRedirect(request.getContextPath() + "/company/read");
                return;
            }

            System.err.println("[COMPANY-DELETE] Unknown error.");
            request.setAttribute("errorMessage", "Unable to delete (Unknown)");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);

        } catch (NullPointerException npe) {
            System.err.println("[COMPANY-DELETE] NullPointerException.");
            request.setAttribute("errorMessage", "Unable to delete (Unknown)");
            request.getRequestDispatcher("/html/crud/company/error/error.jsp").forward(request, response);
        }
    }
}
/*
 * BUSINESS RULES (DO NOT DELETE):
 * If the company is set as deactivated, it will be treated the same as a deleted company; this means, it cannot make any operation.
 * Deleted/deactivated companies may be visible depending on the used filter.
 * */
