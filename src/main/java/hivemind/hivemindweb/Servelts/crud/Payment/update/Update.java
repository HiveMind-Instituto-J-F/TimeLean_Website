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
            String methodParam = req.getParameter("method");
            String beneficiaryParam = req.getParameter("beneficiary");
            String deadlineParam = req.getParameter("deadline");
            String statusParam = req.getParameter("status");

            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Null value: 'id'");
            if (methodParam != null) methodParam = methodParam.trim();
            if (beneficiaryParam == null || beneficiaryParam.isEmpty()) throw new IllegalArgumentException("Null value:'beneficiary'");
            if (deadlineParam == null || deadlineParam.isEmpty()) throw new IllegalArgumentException("Null value:'deadline'");
            if (statusParam == null || statusParam.isEmpty()) throw new IllegalArgumentException("Null value:'status'");

            LocalDate deadline = LocalDate.parse(deadlineParam);
            int id = Integer.parseInt(idParam);

            // [LOGIC] Create payment object
            Payment paymentLocal = new Payment(id, deadline, methodParam, beneficiaryParam, statusParam);

            // [DATA ACCESS] Attempt to update payment
            if (PaymentDAO.update(paymentLocal)) {
                // [SUCCESS LOG] Payment updated successfully
                System.err.println("[INFO] Payment updated successfully, id: " + id);
                resp.sendRedirect(req.getContextPath() + "/payment/read");
            } else {
                // [FAILURE LOG] Failed database update
                System.err.println("[ERROR] Failed to update payment, id: " + id);
                req.setAttribute("errorMessage", "Pagamento não foi atualizado devido a um erro no banco de dados.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameters
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Date parsing error
            System.err.println("[ERROR] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao atualizar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update?id=" + req.getParameter("id"));
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
