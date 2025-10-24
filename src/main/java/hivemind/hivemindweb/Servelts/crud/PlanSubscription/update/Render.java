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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[INFO] Entered Render PlanSubscription servlet");

        try {
            // Get and validate 'id' parameter
            int id = Integer.parseInt(req.getParameter("id"));
            System.out.println("[INFO] Received id parameter: " + id);
            if (id == 0) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado ou inválido.");
            }

            // Retrieve plan_subscription from database
            PlanSubscription planSubscription = PlanSubscriptionDAO.select(id);
            if (planSubscription == null) {
                // Treat case when plan_subscription is not found
                System.err.println("[WARN] PlanSubscription not found for id: " + id);
                req.setAttribute("errorMessage", "Assinatura não encontrada.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                System.out.println("[INFO] Servlet exiting with failure (PlanSubscription not found)");
                return;
            }

            // Forward to update page with plan_subscription
            System.out.println("[INFO] PlanSubscription retrieved successfully: " + planSubscription);
            req.setAttribute("planSubscription", planSubscription);
            req.getRequestDispatcher("/html/crud/planSubscription/update.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting successfully");

        } catch (IllegalArgumentException ia) {
            // Handle invalid parameters
            System.err.println("[ERROR] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (IllegalArgumentException)");

        } catch (ServletException se) {
            // Handle servlet dispatch errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (ServletException)");

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar a assinatura.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan_subscription/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (Exception)");
        }
    }
}