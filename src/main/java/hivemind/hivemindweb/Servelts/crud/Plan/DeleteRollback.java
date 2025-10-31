package hivemind.hivemindweb.Servelts.crud.Plan;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/delete/rollback")
public class DeleteRollback extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate and parse 'id' parameter
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Parameter 'id' is required but was null or empty");
            }

            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve plan by ID from database
            Plan plan = PlanDAO.selectByID(id);
            plan.setActive(true);

            // [PROCESS] Attempt to update the plan in database
            if (PlanDAO.update(plan)) {
                // [SUCCESS LOG] Log successful rollback
                System.err.println("[INFO] Plan rollback successfully processed. ID: " + id);
                resp.sendRedirect(req.getContextPath() + "/plan/read");
                return;
            }

            // [FAILURE LOG] Unknown failure when updating plan
            System.err.println("[ERROR] Failed to update plan during rollback. ID: " + id);
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor ao reativar o plano.");
            req.getRequestDispatcher("/html/crud/plan/create.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Handle invalid argument exception
            System.err.println("[ERROR] IllegalArgumentException occurred: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao processar o rollback: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] NullPointerException occurred: " + npe.getMessage());
            req.setAttribute("errorMessage", "O plano não foi encontrado para reativação.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Handle servlet exception
            System.err.println("[ERROR] ServletException occurred: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao redirecionar a página: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] Handle IO exception
            System.err.println("[ERROR] IOException occurred: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de entrada/saída ao processar o rollback: " + ioe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Handle unexpected exceptions
            System.err.println("[ERROR] Unexpected exception occurred: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
