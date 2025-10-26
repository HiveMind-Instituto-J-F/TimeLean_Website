package hivemind.hivemindweb.Servelts.crud.Company;

import java.io.IOException;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/company/delete/rollback")
public class DeleteRollback extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve and validate 'cnpj' parameter
            String paramCnpj = req.getParameter("cnpj");
            if (paramCnpj == null || paramCnpj.isEmpty()) {
                throw new IllegalArgumentException("Null value: 'cnpj'");
            }

            // [PROCESS] Attempt to reactivate (rollback delete) the company
            Company company = new Company(paramCnpj);
            if (CompanyDAO.switchActive(company, true)) {
                // [SUCCESS LOG] Company reactivated successfully
                System.err.println("[INFO] Company reactivated: " + paramCnpj);
                resp.sendRedirect(req.getContextPath() + "/company/read");
                return;
            }

            // [FAILURE LOG] Unknown error during reactivation
            System.err.println("[ERROR] Unknown error reactivating company: " + paramCnpj);
            req.setAttribute("errorMessage", "Não foi possível reativar a empresa (Erro desconhecido).");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
