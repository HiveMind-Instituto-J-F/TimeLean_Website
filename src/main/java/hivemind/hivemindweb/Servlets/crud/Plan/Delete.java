package hivemind.hivemindweb.Servlets.crud.Plan;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/delete")
public class Delete extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate and parse ID parameter
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Valor nulo: 'id'");
            }

            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve plan by ID from database
            Plan planDb = PlanDAO.selectByID(id);
            if (planDb == null) {
                throw new NullPointerException("Plano não encontrado no banco de dados");
            }

            // [BUSINESS RULES] Disable plan
            planDb.setActive(false);

            // [PROCESS] Attempt to update plan status
            if (PlanDAO.update(planDb)) {
                // [SUCCESS LOG] Log successful plan deactivation
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Plan successfully deactivated: " + planDb.getName());
            } else {
                // [BUSINESS RULES] Plan already inactive
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Plan already inactive: " + planDb.getName());
            }

            resp.sendRedirect(req.getContextPath() + "/plan/read");

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Handle invalid argument exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException occurred: " + ia.getMessage());
            req.setAttribute("errorMessage", "Não foi possível processar a solicitação de exclusão do plano. Verifique o ID informado e tente novamente. Se o problema persistir, contate o suporte. Detalhes: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException occurred: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao localizar o plano. Verifique se o plano existe.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] Handle IO exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IOException occurred: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de entrada/saída ao processar a solicitação: " + ioe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
