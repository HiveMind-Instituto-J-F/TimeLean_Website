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
        System.out.println("[INFO] [" + LocalDateTime.now() + "] Entered Create PlanSubscription servlet");

        try {
            // Validate and parse start date
            String startDateStr = req.getParameter("start_date");
            if (startDateStr == null || startDateStr.isEmpty()) {
                throw new IllegalArgumentException("Data de início não informada.");
            }
            LocalDate startDate = LocalDate.parse(startDateStr);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received start_date: " + startDate);

            // Validate company CNPJ
            String cnpjCompany = req.getParameter("cnpj_company");
            if (cnpjCompany == null || cnpjCompany.isEmpty()) {
                throw new IllegalArgumentException("CNPJ da empresa não informado.");
            }
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received cnpj_company: " + cnpjCompany);

            // Validate plan ID
            String idPlanStr = req.getParameter("id_plan");
            if (idPlanStr == null || idPlanStr.isEmpty()) {
                throw new IllegalArgumentException("ID do plano não informado.");
            }
            int idPlan = Integer.parseInt(idPlanStr);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received id_plan: " + idPlan);

            // Validate number of installments
            String numberInstallmentsStr = req.getParameter("number_installments");
            if (numberInstallmentsStr == null || numberInstallmentsStr.isEmpty()) {
                throw new IllegalArgumentException("Número de parcelas não informado.");
            }
            int numberInstallments = Integer.parseInt(numberInstallmentsStr);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received number_installments: " + numberInstallments);

            // Validate status
            String statusStr = req.getParameter("status");
            if (statusStr == null || statusStr.isEmpty()){
                throw new IllegalArgumentException("Status inicial não fornecido");
            }
            boolean status = Boolean.parseBoolean(statusStr);

            // Create and insert PlanSubscription
            PlanSubscription planSubscriptionLocal = new PlanSubscription(startDate, cnpjCompany, idPlan, numberInstallments);
            boolean inserted = PlanSubscriptionDAO.insert(planSubscriptionLocal, false);

            // Check for existing active plan subscriptions
            planSubscriptionLocal.setStatus(status);
            List<PlanSubscription> activePlans = PlanSubscriptionDAO.selectActivePlans(cnpjCompany);
            for (PlanSubscription ps : activePlans) {
                if (ps.getStatus() && planSubscriptionLocal.getStatus()) {
                    throw new IllegalArgumentException("Já existe uma assinatura ativa para esta empresa.");
                }
            }

            if (inserted) {
                System.out.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription.Create -> Record successfully created.");
            } else {
                throw new IllegalStateException("Falha ao inserir a assinatura no banco de dados.");
            }

            // Redirect after success
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting successfully");

        } catch (DateTimeParseException dpe) {
            // Handle invalid date format
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Formato de data inválido: " + dpe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan_subscription/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (DateTimeParseException)");

        } catch (IllegalArgumentException | IllegalStateException e) {
            // Handle invalid parameters, business rule violations, or database insert failure
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscription.Create -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao criar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan_subscription/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (" + e.getClass().getSimpleName() + ")");

        } catch (Exception e) {
            // Catch-all for unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao criar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan_subscription/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (Exception)");
        }
    }
}