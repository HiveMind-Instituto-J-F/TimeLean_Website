package hivemind.hivemindweb.Servlets.crud.PlanSubscription;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // [PROCESS] Handle deletion of a PlanSubscription
        try {
            String idParam = req.getParameter("id");

            // [VALIDATION] Ensure ID parameter is provided
            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Null value: 'id'");
            int id = Integer.parseInt(idParam);


            // [DATA ACCESS] Select PlanSubscription, set status to false and update
            PlanSubscription planSubscriptionFromDb = PlanSubscriptionDAO.select(id);
            planSubscriptionFromDb.setStatus(false);

            boolean deleted = PlanSubscriptionDAO.update(planSubscriptionFromDb);
            if (!deleted) {
                throw new IllegalStateException("Failed while trying to delete plan subscription (ID: " + id + ").");
            }

            System.err.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription deleted successfully (ID: " + id + ")");
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");

        }catch (IllegalArgumentException | IllegalStateException | IOException e) {
            // [FAILURE LOG] Invalid arguments, deletion failure, or I/O error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos, Por favor, preencha todos os campos corretamente. Erro: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
