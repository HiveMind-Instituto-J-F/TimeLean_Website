package hivemind.hivemindweb.Servelts.crud.Company.update;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/company/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Receive and validate form parameters
            String paramCnpj = req.getParameter("cnpj");
            if (paramCnpj == null || paramCnpj.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cnpj'");

            String paramName = req.getParameter("name");
            if (paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'name'");

            String paramCnae = req.getParameter("cnae");
            if (paramCnae == null || paramCnae.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'cnae'");

            String paramRegistrantCpf = req.getParameter("registrantCpf");
            if (paramRegistrantCpf == null || paramRegistrantCpf.isEmpty()) throw new IllegalArgumentException("Values Is Null, Value: 'registrantCpf'");

            // [PROCESS] Create Company object with updated data
            Company company = new Company(paramCnpj, paramName, paramCnae, paramRegistrantCpf, true);

            // [DATA ACCESS] Attempt to update the company
            if (CompanyDAO.update(company)) {
                // [SUCCESS LOG] Company updated successfully
                System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Company updated successfully: " + paramCnpj);
                resp.sendRedirect(req.getContextPath() + "/company/read");
            } else {
                // [FAILURE LOG] Failed to update company in DB
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Failed to update company: " + paramCnpj);
                req.setAttribute("company", company);
                req.setAttribute("errorMessage", "Falha ao atualizar a empresa.");
                req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameters
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatching error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
