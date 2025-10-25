package hivemind.hivemindweb.Servelts.crud.Company;

import java.io.IOException;
import java.util.List;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/company/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve and validate 'cnpj' parameter
            String paramCnpj = req.getParameter("cnpj");
            if (paramCnpj == null || paramCnpj.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");
            }

            // [DATA ACCESS] Check for pending payments
            Company company = new Company(paramCnpj);
            List<Payment> pendingPayments = PaymentDAO.selectPendingPayments(paramCnpj);

            // [BUSINESS RULES] Prevent deletion if pending payments exist
            if (pendingPayments != null && !pendingPayments.isEmpty()) {
                throw new IllegalArgumentException("Company has pending payments and cannot be deleted");
            }

            // [PROCESS] Attempt to switch company active status (soft delete)
            if (CompanyDAO.switchActive(company, company.isActive())) {
                // [SUCCESS LOG] Company deleted/deactivated successfully
                System.err.println("[SUCCESS LOG] Company deleted/deactivated: " + paramCnpj);
                resp.sendRedirect(req.getContextPath() + "/company/read");
                return;
            }

            // [FAILURE LOG] Unknown deletion error
            System.err.println("[FAILURE LOG] Unknown error deleting company: " + paramCnpj);
            req.setAttribute("errorMessage", "Não foi possível deletar a empresa (Erro desconhecido).");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered
            System.err.println("[FAILURE LOG] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Não foi possível deletar a empresa (Erro interno).");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input or business rule violation
            System.err.println("[FAILURE LOG] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[FAILURE LOG] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE LOG] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}