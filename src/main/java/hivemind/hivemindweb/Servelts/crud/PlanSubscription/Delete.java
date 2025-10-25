package hivemind.hivemindweb.Servelts.crud.PlanSubscription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Exception.InvalidPrimaryKeyException;
import hivemind.hivemindweb.models.PlanSubscription;

@WebServlet("/plan_subscription/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // [PROCESS] Handle deletion of a PlanSubscription
        try {
            String idParam = req.getParameter("id");

            // [VALIDATION] Ensure ID parameter is provided
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idParam);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received id: " + id);

            PlanSubscription planSubscriptionLocal = new PlanSubscription(id);

            // [DATA ACCESS] Attempt to delete PlanSubscription
            boolean deleted = PlanSubscriptionDAO.delete(planSubscriptionLocal);
            if (!deleted) {
                throw new IllegalStateException("Falha ao deletar a assinatura (ID: " + id + ").");
            }

            System.err.println("[SUCCESS] [" + LocalDateTime.now() + "] PlanSubscription deleted successfully (ID: " + id + ")");
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");

        } catch (InvalidPrimaryKeyException ipk) {
            // [FAILURE LOG] Invalid primary key or foreign key issue
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] InvalidPrimaryKeyException: " + ipk.getMessage());
            req.setAttribute("errorMessage", "Chave primária inválida ou referência inexistente: " + ipk.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            // [FAILURE LOG] Invalid arguments, deletion failure, or I/O error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao deletar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao deletar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
