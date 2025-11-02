package hivemind.hivemindweb.Servelts.crud.Plan.update;

import java.io.IOException;
import java.time.LocalDate;
import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/render-update")
public class Render extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate and parse plan ID parameter
            String idParam = req.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("Values Is Null, Value: 'id'");
            }
            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve plan by ID
            Plan planLocal;
            try {
                planLocal = PlanDAO.selectByID(id);
                if (planLocal == null) {
                    // [FAILURE LOG] Plan not found in database
                    System.err.println("[ERROR] [" + LocalDate.now() + "] Plan is null");
                    req.setAttribute("errorMessage", "O plano solicitado não foi encontrado.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // [FAILURE LOG] NullPointerException while accessing plan
                System.err.println("[ERROR] [" + LocalDate.now() + "] NullPointerException while selecting plan");
                req.setAttribute("errorMessage", "Erro interno ao acessar o plano solicitado.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Attach plan to request and render update page
            req.setAttribute("plan", planLocal);
            req.getRequestDispatcher("/html/crud/plan/update.jsp").forward(req, resp);

            // [SUCCESS LOG] Successful rendering
            System.out.println("[INFO] [" + LocalDate.now() + "] Plan render executed successfully");

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid argument in servlet
            System.err.println("[ERROR] [" + LocalDate.now() + "] Invalid argument in Render Servlet: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno ao processar o plano.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] NullPointerException in Render Servlet
            System.err.println("[ERROR] [" + LocalDate.now() + "] NullPointerException in Render Servlet: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: valor nulo encontrado durante o processamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatching error
            System.err.println("[ERROR] [" + LocalDate.now() + "] Servlet dispatch error: " + se.getMessage());
            req.setAttribute("errorMessage", "Falha ao carregar a página de atualização do plano.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] IOException during request handling
            System.err.println("[ERROR] [" + LocalDate.now() + "] IOException while processing Render Servlet: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de comunicação com o servidor.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] [" + LocalDate.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao processar o plano.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
