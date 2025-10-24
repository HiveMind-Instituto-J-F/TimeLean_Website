package hivemind.hivemindweb.Servelts.crud.PlanSubscription.update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.PlanSubscription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan_subscription/update")
public class Update extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("[INFO] Entered Update PlanSubscription servlet");

        try {
            // Get and validate 'id' parameter
            String idParam = req.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idParam);
            System.out.println("[INFO] Received id: " + id);

            // Get and validate 'start_date' parameter
            String startDateStr = req.getParameter("start_date");
            if (startDateStr == null || startDateStr.isEmpty()) {
                throw new IllegalArgumentException("Data de início não informada.");
            }
            LocalDate startDate = LocalDate.parse(startDateStr);
            System.out.println("[INFO] Received start_date: " + startDate);

            // Get and validate 'status' parameter
            String statusParam = req.getParameter("status");
            if (statusParam == null || statusParam.isEmpty()) {
                throw new IllegalArgumentException("Status não informado.");
            }
            boolean status = Boolean.parseBoolean(statusParam);
            System.out.println("[INFO] Received status: " + status);

            // Create local PlanSubscription object
            PlanSubscription planSubscriptionLocal = new PlanSubscription(id, startDate, status);

            // Get and validate 'cnpj_company' parameter
            String cnpjCompany = req.getParameter("cnpj_company");
            if (cnpjCompany == null || cnpjCompany.isEmpty()) {
                throw new IllegalArgumentException("CNPJ da empresa não informado.");
            }
            System.out.println("[INFO] Received cnpj_company: " + cnpjCompany);

            // Check for existing active plan subscriptions
            List<PlanSubscription> activePlans = PlanSubscriptionDAO.selectActivePlans(cnpjCompany);
            for (PlanSubscription ps : activePlans) {
                if (ps.getId() != id && ps.getStatus() && planSubscriptionLocal.getStatus()) {
                    throw new IllegalArgumentException("Já existe uma assinatura ativa para esta empresa.");
                }
            }

            // Update PlanSubscription
            if (PlanSubscriptionDAO.update(planSubscriptionLocal)) {
                System.out.println("[INFO] PlanSubscription updated successfully");
            } else {
                System.err.println("[WARN] PlanSubscription could not be updated due to an internal error");
                req.setAttribute("errorMessage", "Não foi possível atualizar a assinatura. Verifique se ela existe.");
                req.setAttribute("errorUrl", req.getContextPath() + "/company/read?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                System.out.println("[INFO] Servlet exiting with failure (update failed)");
                return;
            }

            // Redirect to read page on success
            resp.sendRedirect(req.getContextPath() + "/plan_subscription/read");
            System.out.println("[INFO] Servlet exiting successfully");

        } catch (IllegalArgumentException iae) {
            // Handle invalid parameters or business rule violations
            // e.g., missing id, missing start_date, missing status, or duplicate active plan
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (IllegalArgumentException)");

        } catch (DateTimeParseException dpe) {
            // Handle invalid date format when parsing start_date
            System.err.println("[ERROR] Failed to parse date: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Formato de data inválido: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (DateTimeParseException)");

        } catch (NullPointerException npe) {
            // Handle unexpected null values
            System.err.println("[ERROR] Null elements: " + npe.getMessage());
            req.setAttribute("errorMessage", "Elementos nulos: " + npe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (NullPointerException)");

        } catch (Exception e) {
            // Catch-all for any other unexpected exceptions
            System.err.println("[ERROR] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar a assinatura.");
            req.setAttribute("errorUrl", req.getContextPath() + "/company/read?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            System.out.println("[INFO] Servlet exiting with failure (Exception)");
        }
    }
}
