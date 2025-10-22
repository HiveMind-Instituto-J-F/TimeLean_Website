package hivemind.hivemindweb.Servelts.Payment.update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/update")
public class Update extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get and validate parameters
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idStr);

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

            String status = req.getParameter("status");
            if (status == null || status.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'status' não informado.");
            }

            // Create payment object
            Payment paymentLocal = new Payment(id, deadline, method, beneficiary, status);

            // Attempt update
            if (PaymentDAO.update(paymentLocal)) {
                System.out.println("[INF] Pagamento atualizado com sucesso.");
                resp.sendRedirect(req.getContextPath() + "/payment/read");
            } else {
                System.err.println("[WARN] Falha na atualização do pagamento.");
                req.setAttribute("errorMessage", "Pagamento não foi atualizado devido a um erro no banco de dados.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/update");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // Handle invalid input
            System.err.println("[ERROR] Entrada inválida: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // Handle date parsing errors
            System.err.println("[ERROR] Falha ao converter data: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // Handle servlet dispatch errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Exception inesperada: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
