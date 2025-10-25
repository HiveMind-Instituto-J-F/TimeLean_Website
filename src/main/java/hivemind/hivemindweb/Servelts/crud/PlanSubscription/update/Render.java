package hivemind.hivemindweb.Servelts.crud.PlanSubscription.update;

import java.io.IOException;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/render-update")
public class Render extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [PROCESS] Retrieve plan subscription by ID and forward to update page
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado ou inválido.");
            }
            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve plan subscription from database
            PlanSubscription planSubscription = PlanSubscriptionDAO.select(id);
            if (planSubscription == null) {
                // [FAILURE LOG] Plan subscription not found
                System.err.println("[FAILURE] PlanSubscription not found for id: " + id);
                req.setAttribute("errorMessage", "Assinatura não encontrada.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [SUCCESS LOG] Forward plan subscription to update page
            System.err.println("[SUCCESS] PlanSubscription retrieved successfully: " + planSubscription);
            req.setAttribute("planSubscription", planSubscription);
            req.getRequestDispatcher("/html/crud/planSubscription/update.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid ID parameter
            System.err.println("[FAILURE] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch errors
            System.err.println("[FAILURE] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected errors
            System.err.println("[FAILURE] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar a assinatura.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
