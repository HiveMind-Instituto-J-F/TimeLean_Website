package hivemind.hivemindweb.Servelts.PlanSubscription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.Exception.InvalidPrimaryKeyException;
import hivemind.hivemindweb.models.PlanSubscription;

@WebServlet("/plan_subscription/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("[INFO] [" + LocalDateTime.now() + "] Entered Delete PlanSubscription servlet");

        try {
            // Get and validate ID parameter
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idStr);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Received id: " + id);

            // Create local PlanSubscription object
            PlanSubscription planSubscriptionLocal = new PlanSubscription(id);

            // Attempt to delete
            boolean deleted = PlanSubscriptionDAO.delete(planSubscriptionLocal);

            if (deleted) {
                System.out.println("[INFO] [" + LocalDateTime.now() + "] PlanSubscription.Delete -> Record successfully deleted (ID: " + id + ")");
                resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting successfully");
                return;
            }

            // If deletion failed without exception
            throw new IllegalStateException("Falha ao deletar a assinatura (ID: " + id + ").");

        } catch (InvalidPrimaryKeyException ipk) {
            // Handle invalid primary key or foreign key issues
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] InvalidPrimaryKeyException: " + ipk.getMessage());
            req.setAttribute("errorMessage", "Chave primária inválida ou referência inexistente: " + ipk.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (InvalidPrimaryKeyException)");

        } catch (DateTimeParseException dpe) {
            // Handle errors parsing dates, if applicable
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Erro ao interpretar data: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (DateTimeParseException)");

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            // Handle invalid arguments, deletion failures, or I/O exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscription.Delete -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao deletar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (" + e.getClass().getSimpleName() + ")");

        } catch (Exception e) {
            // Catch-all for unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao deletar a assinatura: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Servlet exiting with failure (Exception)");
        }
    }
}
