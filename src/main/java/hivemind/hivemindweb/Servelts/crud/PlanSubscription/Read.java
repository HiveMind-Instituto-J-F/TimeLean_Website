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
        // [PROCESS] Handle retrieval of PlanSubscription list
        try {
            FilterType.PlanSubscription filterType = FilterType.PlanSubscription.ALL_VALUES;
            String filter = null;

            String cnpjCompanyParam = req.getParameter("cnpj_company");
            String idPlanParam = req.getParameter("id_plan");

            // [LOGIC] Determine filter type
            if (cnpjCompanyParam != null && !cnpjCompanyParam.isEmpty()) {
                filterType = FilterType.PlanSubscription.CNPJ_COMPANY;
                filter = cnpjCompanyParam;
            } else if (idPlanParam != null && !idPlanParam.isEmpty()) {
                filterType = FilterType.PlanSubscription.ID_PLAN;
                filter = idPlanParam;
            }

            // [DATA ACCESS] Retrieve filtered list of PlanSubscriptions
            List<PlanSubscription> planSubList = PlanSubscriptionDAO.selectFilter(filterType, filter);

           // [SUCCESS LOG] log success and forward to read
            System.out.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription.Read -> List successfully loaded. Total: " + planSubList.size());
            req.setAttribute("planSubs", planSubList);
            req.getRequestDispatcher("/WEB-INF/view/crud/planSubscription/read.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao carregar as assinaturas: elemento nulo encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/pages/chooser.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException | ServletException | IOException e) {
            // [FAILURE LOG] Handle invalid params, servlet forwarding or IO errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscription.Read -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao carregar as assinaturas: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/pages/chooser.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao carregar as assinaturas: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/pages/chooser.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
