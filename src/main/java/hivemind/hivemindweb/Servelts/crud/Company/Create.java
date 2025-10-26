package hivemind.hivemindweb.Servelts.crud.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

@WebServlet("/company/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve and validate form parameters
            String paramCnpj = req.getParameter("company-cnpj");
            String paramName = req.getParameter("company-name");
            String paramCnae = req.getParameter("company-cnae");
            String paramRegistrantCpf = req.getParameter("company-registrant-cpf");

            if (paramCnpj == null || paramCnpj.isEmpty()) throw new IllegalArgumentException("Null value: 'cnpj'");
            if (paramName == null || paramName.isEmpty()) throw new IllegalArgumentException("Null value: 'name'");
            if (paramCnae == null || paramCnae.isEmpty()) throw new IllegalArgumentException("Null value: 'cnae'");
            if (paramRegistrantCpf == null || paramRegistrantCpf.isEmpty()) throw new IllegalArgumentException("Null value: 'registrantCpf'");

            // [PROCESS] Create Company object
            Company company = new Company(paramCnpj, paramName, paramCnae, paramRegistrantCpf, true);

            // [DATA ACCESS] Attempt to insert company in database
            if (CompanyDAO.insert(company)) {
                // [SUCCESS LOG] Company inserted successfully
                System.err.println("[INFO] [" + LocalDateTime.now() + "] Company created successfully: " + paramCnpj);

                // [PROCESS] redirect to /company/read
                resp.sendRedirect(req.getContextPath() + "/company/read");
            } else {
                // [FAILURE LOG] Failed to insert company
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to create company: " + paramCnpj);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Date parsing error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
