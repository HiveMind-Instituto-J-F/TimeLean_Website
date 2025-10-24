package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import java.util.List;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@WebServlet("/payment/read")
public class Read extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            // Get filters
            FilterType.Payment filterType = FilterType.Payment.ALL_VALUES;
            Integer filter = null;

            String statusParam = req.getParameter("status");
            String idParam = req.getParameter("idPlanSubscription");

            String status = statusParam != null && !statusParam.isEmpty() ? statusParam : null;
            Integer idPlanSubscription = idParam != null && !idParam.isEmpty() ? Integer.parseInt(idParam) : -1;

            // Determine filter type based on parameters
            if (idPlanSubscription != -1) {
                filterType = FilterType.Payment.ID_PLAN_SUBSCRIPTION;
            } else if (status != null) {
                idPlanSubscription = -1;
                if ("pending".equals(status)) {
                    filterType = FilterType.Payment.PENDING;
                } else if ("paid".equals(status)) {
                    filterType = FilterType.Payment.PAID;
                } else if ("canceled".equals(status)) {
                    filterType = FilterType.Payment.CANCELED;
                } else if (!"all".equals(status)) {
                    // Invalid filter
                    System.err.println("[WARN] Invalid filter.");
                    req.setAttribute("errorMessage", "Filtro inválido informado.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            }

            // Retrieve filtered payments
            List<Payment> paymentList = PaymentDAO.selectFilter(filterType, idPlanSubscription);
            System.out.println("[INF] [" + LocalDateTime.now() + "] Payment.Read -> Lista carregada com sucesso. Total: " + paymentList.size());

            // Forward to payment list page
            req.setAttribute("payments", paymentList);
            req.getRequestDispatcher("/html/crud/payment/read.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // Handle invalid argument errors
            System.err.println("[ERROR] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados. Verifique os valores e tente novamente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // Handle null pointer exceptions
            System.err.println("[ERROR] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: dado necessário não foi encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // Handle servlet dispatch errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar os pagamentos.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
