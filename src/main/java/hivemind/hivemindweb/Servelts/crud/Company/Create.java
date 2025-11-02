package hivemind.hivemindweb.Servelts.crud.Company;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/company/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate incoming request parameters
            String paramCnpj = req.getParameter("company-cnpj");
            String paramName = req.getParameter("company-name");
            String paramCnae = req.getParameter("company-cnae");
            String paramRegistrantCpf = req.getParameter("company-registrant-cpf");

            if (paramCnpj == null || paramCnpj.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'CNPJ'");
            }
            if (paramName == null || paramName.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'Nome da empresa'");
            }
            if (paramCnae == null || paramCnae.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'CNAE'");
            }
            if (paramRegistrantCpf == null || paramRegistrantCpf.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'CPF Do registrante'");
            }

            // [PROCESS] Instantiate Company object
            Company company = new Company(paramCnpj, paramName, paramCnae, paramRegistrantCpf, true);

            // [DATA ACCESS] Insert company into database
            boolean insertSuccess = CompanyDAO.insert(company);

            if (insertSuccess) {
                // [SUCCESS LOG] Log successful creation
                System.err.println("[INFO] [" + LocalDateTime.now() + "] [Create - Company] Company created successfully: " + paramCnpj);

                // [PROCESS] Redirect to company read page
                resp.sendRedirect(req.getContextPath() + "/company/read");
            } else {
                // [FAILURE LOG] Log failure to insert
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Create - Company] Failed to create company: " + paramCnpj);
                req.setAttribute("errorMessage", "Não foi possível cadastrar a empresa. Tente novamente.");
                req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Log invalid argument error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Create - Company] IllegalArgumentException: Invalid Arguments");
            req.setAttribute("errorMessage", "Dados inválidos. Por favor, preencha todos os campos corretamente. Erro: " + ia.getMessage());
            req.getRequestDispatcher("/pages/create/company.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Log null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Create - Company] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado: valor nulo encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Log date parsing error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Create - Company] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a data: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Log unexpected exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Create - Company] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
