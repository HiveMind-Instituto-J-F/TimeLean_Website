package hivemind.hivemindweb.Servelts.crud.Plant;

import java.io.IOException;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@WebServlet("/plant/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AAAAAAAA");
        // [PROCESS] Handle plant creation submission
        try {
            // [VALIDATION] Get and validate parameters
            String cnpjParam = req.getParameter("cnpj");
            String cnaeParam = req.getParameter("cnae");
            String operationalStatusStringParam = req.getParameter("operational_status");
            String responsibleCpfParam = req.getParameter("responsible_cpf");
            String addressCepParam = req.getParameter("address_cep");
            int addressNumberParam = Integer.parseInt(req.getParameter("address_number"));
            String companyCnpjParam = req.getParameter("company_cnpj");

            boolean operationalStatusParam = "Active".equalsIgnoreCase(operationalStatusStringParam);

            // [LOGIC] Create Plant object
            Plant plantLocal = new Plant(cnpjParam, cnaeParam, responsibleCpfParam,
                    operationalStatusParam, addressCepParam, addressNumberParam, companyCnpjParam);
            System.out.println(plantLocal);

            // [DATA ACCESS] Insert Plant into database
            boolean inserted = PlantDAO.insert(plantLocal);
            if (inserted) {
                System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Plant created successfully: " + cnpjParam);
                resp.sendRedirect(req.getContextPath() + "/plant/read");
            } else {
                throw new IllegalStateException("Falha ao inserir a planta no banco de dados.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Handle validation or insertion failures
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao criar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plant/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao criar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plant/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}