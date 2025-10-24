package hivemind.hivemindweb.Servelts.crud.PlanSubscription;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/plan_subscription/read")
public class Read extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[INFO] [" + LocalDateTime.now() + "] Entered Read PlanSubscription servlet");

        try {
            // Define default filter
            FilterType.PlanSubscription filterType = FilterType.PlanSubscription.ALL_VALUES;
            String filter = null;

            // Read request parameters
            String cnpjCompany = req.getParameter("cnpj_company");
            String idPlan = req.getParameter("id_plan");

            // Set filter type based on parameters
            if (cnpjCompany != null && !cnpjCompany.isEmpty()) {
                filterType = FilterType.PlanSubscription.CNPJ_COMPANY;
                filter = cnpjCompany;
            } else if (idPlan != null && !idPlan.isEmpty()) {
                filterType = FilterType.PlanSubscription.ID_PLAN;
                filter = idPlan;
            }

            // Retrieve filtered list
            List<PlanSubscription> planSubList = PlanSubscriptionDAO.selectFilter(filterType, filter);

            // Null check
            if (planSubList == null) {
                throw new NullPointerException("A lista retornada é nula (PlanSubscriptionDAO.selectFilter).");
            }

            // Log success
            System.out.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription.Read -> Lista carregada com sucesso. Total: " + planSubList.size());

            // Forward data to JSP
            req.setAttribute("planSubs", planSubList);
            req.getRequestDispatcher("/html/crud/planSubscription/read.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting successfully");

        } catch (IllegalArgumentException | NullPointerException | ServletException | IOException e) {
            // Handle expected errors like invalid parameters, null list, servlet forwarding issues, or IO problems
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscription.Read -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao carregar as assinaturas: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (" + e.getClass().getSimpleName() + ")");

        } catch (Exception e) {
            // Catch-all for unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao carregar as assinaturas: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (Exception)");
        }
    }
}
