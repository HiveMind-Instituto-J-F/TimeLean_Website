package hivemind.hivemindweb.Servelts.crud.Payment.update;

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [PROCESS] Handle payment update
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idParam);

            String methodParam = req.getParameter("method");
            if (methodParam != null) methodParam = methodParam.trim();

            String beneficiaryParam = req.getParameter("beneficiary");
            if (beneficiaryParam == null || beneficiaryParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'beneficiary' não informado.");
            }

            String deadlineParam = req.getParameter("deadline");
            if (deadlineParam == null || deadlineParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'deadline' não informado.");
            }
            LocalDate deadline = LocalDate.parse(deadlineParam);

            String statusParam = req.getParameter("status");
            if (statusParam == null || statusParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'status' não informado.");
            }

            // [LOGIC] Create payment object
            Payment paymentLocal = new Payment(id, deadline, methodParam, beneficiaryParam, statusParam);

            // [DATA ACCESS] Attempt to update payment
            if (PaymentDAO.update(paymentLocal)) {
                // [SUCCESS LOG] Payment updated successfully
                System.err.println("[SUCCESS] Payment updated successfully, id: " + id);
                resp.sendRedirect(req.getContextPath() + "/payment/read");
            } else {
                // [FAILURE LOG] Failed database update
                System.err.println("[FAILURE] Failed to update payment, id: " + id);
                req.setAttribute("errorMessage", "Pagamento não foi atualizado devido a um erro no banco de dados.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameters
            System.err.println("[FAILURE] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Date parsing error
            System.err.println("[FAILURE] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[FAILURE] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
