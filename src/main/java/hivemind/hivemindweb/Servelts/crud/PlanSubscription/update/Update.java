package hivemind.hivemindweb.Servelts.crud.PlanSubscription.update;

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

@WebServlet("/plan_subscription/update")
public class Update extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // [PROCESS] Update PlanSubscription with validated parameters
            String idParam = req.getParameter("id");
            String startDateParam = req.getParameter("start_date");
            String cnpjCompanyParam = req.getParameter("cnpj_company");

            // [VALIDATION] Validate all required parameters
            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            if (startDateParam == null || startDateParam.isEmpty()) throw new IllegalArgumentException("Data de início não informada.");
            if (cnpjCompanyParam == null || cnpjCompanyParam.isEmpty()) throw new IllegalArgumentException("CNPJ da empresa não informado.");

            int id = Integer.parseInt(idParam);
            LocalDate startDate = LocalDate.parse(startDateParam);

            // [DATA ACCESS] select planSubscription on database
            PlanSubscription planSubscriptionFromDb = PlanSubscriptionDAO.select(id);
            if (planSubscriptionFromDb == null) {
                throw new NullPointerException("PlanSubscription não encontrada para o ID: " + id);
            }

            // [BUSINESS RULES] Check for existing active subscriptions
            List<PlanSubscription> activePlans = PlanSubscriptionDAO.selectActivePlans(cnpjCompanyParam);
            for (PlanSubscription ps : activePlans) {
                if (ps.getId() != id && ps.getStatus() && planSubscriptionFromDb.getStatus()) {
                    throw new IllegalArgumentException("Já existe uma assinatura ativa válida para esta empresa.");
                }
            }

            // [PROCESS] update object
            planSubscriptionFromDb.setStartDate(startDate);

            // [DATA ACCESS] Attempt to update the subscription
            if (PlanSubscriptionDAO.update(planSubscriptionFromDb)) {
                System.out.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription updated successfully");
            } else {
                // [FAILURE LOG] Update failed in database
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscription could not be updated in DB");
                req.setAttribute("errorMessage", "Não foi possível atualizar a assinatura. Verifique se ela existe.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + id);
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                return;
            }

            // [SUCCESS LOG] Redirect to read page
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid parameters or business rule violation
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Invalid date format
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Formato de data inválido: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Unexpected null values
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Assinatura não encontrada ou elementos nulos: " + npe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar a assinatura.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
