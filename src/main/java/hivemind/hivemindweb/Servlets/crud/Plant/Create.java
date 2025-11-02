package hivemind.hivemindweb.Servlets.crud.Plant;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plant/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle plant creation submission
        try {
            // [VALIDATION] Get and validate parameters
            String cnpjParam = req.getParameter("cnpj");
            String cnaeParam = req.getParameter("cnae");
            String operationalStatusStringParam = req.getParameter("operational_status");
            String responsibleCpfParam = req.getParameter("responsible_cpf");
            String addressCepParam = req.getParameter("address_cep");
            String addressNumberRaw = req.getParameter("address_number");
            String companyCnpjParam = req.getParameter("company_cnpj");

            if (cnpjParam == null || cnpjParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'CNPJ'");
            if (cnaeParam == null || cnaeParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'CNAE'");
            if (operationalStatusStringParam == null || operationalStatusStringParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'Status operacional'");
            if (responsibleCpfParam == null || responsibleCpfParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'CPF do responsável'");
            if (addressCepParam == null || addressCepParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'CEP'");
            if (addressNumberRaw == null || addressNumberRaw.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'Número'");
            if (companyCnpjParam == null || companyCnpjParam.isEmpty()) throw new IllegalArgumentException("Valor nulo ou vazio: 'CNPJ da empresa'");

            int addressNumberParam = Integer.parseInt(addressNumberRaw);
            boolean operationalStatusParam = "Active".equalsIgnoreCase(operationalStatusStringParam);

            // [BUSINESS RULES] Ensure company is active
            Company companyFromDb = CompanyDAO.select(companyCnpjParam);
            if (!companyFromDb.isActive()) throw new IllegalArgumentException("Empresa inativa.");

            // [LOGIC] Create Plant object
            Plant plantLocal = new Plant(cnpjParam, cnaeParam, responsibleCpfParam,
                    operationalStatusParam, addressCepParam, addressNumberParam, companyCnpjParam);

            // [DATA ACCESS] Insert Plant into database
            boolean inserted = PlantDAO.insert(plantLocal);
            if (inserted) {
                // [SUCCESS LOG] Plant created successfully
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Planta criada com sucesso: " + cnpjParam);
                resp.sendRedirect(req.getContextPath() + "/plant/read");
            } else {
                throw new IllegalStateException("Falha ao tentar inserir a planta no banco de dados");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Handle validation or insertion failures
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao criar a planta: " + e.getMessage());
            req.getRequestDispatcher("/pages/create/plant.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro ao criar a planta: referência nula encontrada.");
            req.getRequestDispatcher("/pages/create/plant.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Erro inesperado: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao criar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", "/pages/create/plant.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
