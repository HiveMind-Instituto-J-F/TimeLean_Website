package hivemind.hivemindweb.Servlets.crud.PlanSubscription;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/delete/rollback")
public class DeleteRollback extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve and validate 'id' parameter
            String paramId = req.getParameter("id");
            if (paramId == null || paramId.isEmpty()) {
                throw new IllegalArgumentException("Null value: 'id'");
            }
            int id = Integer.parseInt(paramId);

            // [DATA ACCESS] define companyFromDb using CompanyDAO
            PlanSubscription planSubscriptionFromDb = PlanSubscriptionDAO.select(id);

            // [PROCESS] Attempt to reactivate (rollback delete) the company
            planSubscriptionFromDb.setStatus(true);
            if (PlanSubscriptionDAO.update(planSubscriptionFromDb)) {
                // [SUCCESS LOG] Company reactivated successfully
                System.out.println("[INFO] Plan Subscription reactivated: " + id);
                resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");
                return;
            }

            // [FAILURE LOG] Unknown error during reactivation
            System.err.println("[ERROR] Unknown error reactivating company: " + id);
            req.setAttribute("errorMessage", "Não foi possível reativar a assinatura do plano (Erro desconhecido).");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro ao reativar assinatura: elemento nulo encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
