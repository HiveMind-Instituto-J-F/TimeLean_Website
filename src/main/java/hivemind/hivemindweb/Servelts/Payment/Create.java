package hivemind.hivemindweb.Servelts.Payment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/create")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get and validate parameters
            String method = req.getParameter("method");
            if (method != null) method = method.trim();

            String beneficiary = req.getParameter("beneficiary");
            if (beneficiary == null || beneficiary.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'beneficiary' não informado.");
            }

            String deadlineStr = req.getParameter("deadline");
            if (deadlineStr == null || deadlineStr.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'deadline' não informado.");
            }
            LocalDate deadline = LocalDate.parse(deadlineStr);

            String status = "PENDING";

            String idStr = req.getParameter("id_plan_sub");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id_plan_sub' não informado.");
            }
            int id_plan_sub = Integer.parseInt(idStr);

            // Calculate payment value
            double value = PlanDAO.getPrice(id_plan_sub) / PlanSubscriptionDAO.select(id_plan_sub).getNumberInstallments();

            // Validate all required fields
            if (method == null || method.isEmpty() || beneficiary.isEmpty() || deadline == null) {
                throw new ServletException("Valores inválidos ou nulos.");
            }

            // Create payment object
            Payment paymentLocal = new Payment(value, deadline, method, beneficiary, status, id_plan_sub);

            // Insert payment
            if (PaymentDAO.insert(paymentLocal)) {
                System.out.println("[INF] Pagamento adicionado com sucesso.");
                req.setAttribute("msg", "Pagamento foi adicionado com sucesso!");
            } else {
                System.err.println("[WARN] Falha ao adicionar pagamento no banco.");
                req.setAttribute("errorMessage", "Pagamento não foi adicionado devido a um erro no banco de dados.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/create");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException iae) {
            // Handle invalid input
            System.err.println("[ERROR] Entrada inválida: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/create");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // Handle date parsing errors
            System.err.println("[ERROR] Falha ao converter data: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/create");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // Handle servlet errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/create");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Erro inesperado: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao criar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/create");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
