package hivemind.hivemindweb.Servelts.crud.Plan.update;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // [VALIDATION] Validate and parse request parameters
            String idParam = req.getParameter("id");
            String durationParam = req.getParameter("duration");
            String descriptionParam = req.getParameter("description");
            String priceParam = req.getParameter("price");
            String nameParam = req.getParameter("name");

            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Null value: 'id'");
            if (durationParam == null || durationParam.isEmpty()) throw new IllegalArgumentException("Null value: 'duration'");
            if (descriptionParam == null || descriptionParam.isEmpty()) throw new IllegalArgumentException("Null value: 'description'");
            if (priceParam == null || priceParam.isEmpty()) throw new IllegalArgumentException("Null value: 'price'");
            if (nameParam == null || nameParam.isEmpty()) throw new IllegalArgumentException("Null value: 'name'");

            int id = Integer.parseInt(idParam);
            int duration = Integer.parseInt(durationParam);
            double price = Double.parseDouble(priceParam);

            // [DATA ACCESS] get plan and update
            Plan planFromDb = PlanDAO.selectByID(id);
            planFromDb.setDescription(descriptionParam);
            planFromDb.setName(nameParam);
            planFromDb.setDuration(duration);
            planFromDb.setPrice(price);

            // [DATA ACCESS] Attempt to update plan
            if (PlanDAO.update(planFromDb)) {
                // [SUCCESS LOG] Plan successfully updated
                System.err.println("[INFO] Plan updated successfully");
                resp.sendRedirect(req.getContextPath() + "/plan/read");
            } else {
                // [FAILURE LOG] Plan update failed
                System.err.println("[ERROR] Plan not updated due to an internal error");
                req.setAttribute("errorMessage", "O plano não pôde ser atualizado devido a um erro interno.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan/update?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid or missing parameters
            System.err.println("[ERROR] Invalid argument in Update Servlet: " + ia.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos, Por favor, preencha todos os campos corretamente. Erro: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException in Update Servlet: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a atualização do plano.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] IOException during response handling
            System.err.println("[ERROR] IOException in Update Servlet: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de comunicação com o servidor.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
