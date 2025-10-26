package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/read")
public class Read extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [PROCESS] Retrieve and filter payments
            FilterType.Payment filterType = FilterType.Payment.ALL_VALUES;
            int idPlanSubscriptionParam = -1;

            String statusParam = req.getParameter("status");
            String idParam = req.getParameter("idPlanSubscription");

            String status = (statusParam != null && !statusParam.isEmpty()) ? statusParam : null;
            idPlanSubscriptionParam = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : -1;

            // [LOGIC] Determine filter type based on parameters
            if (idPlanSubscriptionParam != -1) {
                filterType = FilterType.Payment.ID_PLAN_SUBSCRIPTION;
            } else if (status != null) {
                switch (status.toLowerCase()) {
                    case "pending" -> filterType = FilterType.Payment.PENDING;
                    case "paid" -> filterType = FilterType.Payment.PAID;
                    case "canceled" -> filterType = FilterType.Payment.CANCELED;
                    case "all" -> filterType = FilterType.Payment.ALL_VALUES;
                    default -> {
                        // [FAILURE LOG] Invalid filter
                        System.err.println("[ERROR] Invalid filter provided: " + status);
                        req.setAttribute("errorMessage", "Filtro inválido informado.");
                        req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                }
            }

            // [DATA ACCESS] Retrieve filtered payments
            List<Payment> paymentList = PaymentDAO.selectFilter(filterType, idPlanSubscriptionParam);
            System.err.println("[INFO] [" + LocalDateTime.now() + "] Payment list loaded successfully. Total: " + paymentList.size());

            // [PROCESS] Forward to payment list page
            req.setAttribute("payments", paymentList);
            req.getRequestDispatcher("/html/crud/payment/read.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid argument errors
            System.err.println("[ERROR] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados. Verifique os valores e tente novamente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null pointer exception
            System.err.println("[ERROR] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: dado necessário não foi encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected errors
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar os pagamentos.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
