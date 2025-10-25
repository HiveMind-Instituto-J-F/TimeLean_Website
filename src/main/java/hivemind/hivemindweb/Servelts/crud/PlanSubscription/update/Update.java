package hivemind.hivemindweb.Servelts.crud.PlanSubscription.update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/update")
public class Update extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // [PROCESS] Update PlanSubscription with validated parameters
        try {
            String idParam = req.getParameter("id");
            String startDateParam = req.getParameter("start_date");
            String statusParam = req.getParameter("status");
            String cnpjCompanyParam = req.getParameter("cnpj_company");

            // [VALIDATION] Validate all required parameters
            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            if (startDateParam == null || startDateParam.isEmpty()) throw new IllegalArgumentException("Data de início não informada.");
            if (statusParam == null || statusParam.isEmpty()) throw new IllegalArgumentException("Status não informado.");
            if (cnpjCompanyParam == null || cnpjCompanyParam.isEmpty()) throw new IllegalArgumentException("CNPJ da empresa não informado.");

            int id = Integer.parseInt(idParam);
            LocalDate startDate = LocalDate.parse(startDateParam);
            boolean status = Boolean.parseBoolean(statusParam);

            PlanSubscription planSubscriptionLocal = new PlanSubscription(id, startDate, status);

            // [BUSINESS RULES] Check for existing active subscriptions
            List<PlanSubscription> activePlans = PlanSubscriptionDAO.selectActivePlans(cnpjCompanyParam);
            for (PlanSubscription ps : activePlans) {
                if (ps.getId() != id && ps.getStatus() && planSubscriptionLocal.getStatus()) {
                    throw new IllegalArgumentException("Já existe uma assinatura ativa para esta empresa.");
                }
            }

            // [DATA ACCESS] Attempt to update the subscription
            if (PlanSubscriptionDAO.update(planSubscriptionLocal)) {
                System.err.println("[SUCCESS] PlanSubscription updated successfully");
            } else {
                // [FAILURE LOG] Update failed in database
                System.err.println("[FAILURE] PlanSubscription could not be updated in DB");
                req.setAttribute("errorMessage", "Não foi possível atualizar a assinatura. Verifique se ela existe.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [SUCCESS LOG] Redirect to read page
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid parameters or business rule violation
            System.err.println("[FAILURE] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Invalid date format
            System.err.println("[FAILURE] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Formato de data inválido: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Unexpected null values
            System.err.println("[FAILURE] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Elementos nulos: " + npe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[FAILURE] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar a assinatura.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
