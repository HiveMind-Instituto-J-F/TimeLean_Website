package hivemind.hivemindweb.Servelts.crud.PlanSubscription;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // [PROCESS] Handle creation of a new PlanSubscription
        try {
            String startDateParam = req.getParameter("start_date");
            String cnpjCompanyParam = req.getParameter("cnpj_company");
            String idPlanParam = req.getParameter("id_plan");
            String numberInstallmentsParam = req.getParameter("number_installments");
            String statusParam = req.getParameter("status");

            // [VALIDATION] Validate required parameters
            if (startDateParam == null || startDateParam.isEmpty()) throw new IllegalArgumentException("Data de início não informada.");
            if (cnpjCompanyParam == null || cnpjCompanyParam.isEmpty()) throw new IllegalArgumentException("CNPJ da empresa não informado.");
            if (idPlanParam == null || idPlanParam.isEmpty()) throw new IllegalArgumentException("ID do plano não informado.");
            if (numberInstallmentsParam == null || numberInstallmentsParam.isEmpty()) throw new IllegalArgumentException("Número de parcelas não informado.");
            if (statusParam == null || statusParam.isEmpty()) throw new IllegalArgumentException("Status inicial não fornecido");

            LocalDate startDate = LocalDate.parse(startDateParam);
            int idPlan = Integer.parseInt(idPlanParam);
            int numberInstallments = Integer.parseInt(numberInstallmentsParam);
            boolean status = Boolean.parseBoolean(statusParam);

            PlanSubscription planSubscriptionLocal = new PlanSubscription(startDate, cnpjCompanyParam, idPlan, numberInstallments, status);

            // [BUSINESS RULES] Ensure no active subscription exists for this company
            List<PlanSubscription> activePlans = PlanSubscriptionDAO.selectActivePlans(cnpjCompanyParam);
            for (PlanSubscription ps : activePlans) {
                if (ps.getStatus() && planSubscriptionLocal.getStatus()) {
                    throw new IllegalArgumentException("Já existe uma assinatura ativa para esta empresa.");
                }
            }

            // [DATA ACCESS] Insert new PlanSubscription
            boolean inserted = PlanSubscriptionDAO.insert(planSubscriptionLocal, false);
            if (!inserted) {
                throw new IllegalStateException("Failed when inserting plan subscription on database.");
            }
            System.err.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription record created successfully");

            // [SUCCESS LOG] Redirect to read page
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Invalid date format
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Formato de data inválido: " + dpe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/planSubscription/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Invalid input or business rule violation
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos, Por favor, preencha todos os campos corretamente. Erro: " + e.getMessage());
            req.getRequestDispatcher("/html/crud/planSubscription/create.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao criar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/planSubscription/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
