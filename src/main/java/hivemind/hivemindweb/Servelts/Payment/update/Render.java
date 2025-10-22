package hivemind.hivemindweb.Servelts.Payment.update;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/render-update")
public class Render extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get and validate parameter
            int id = Integer.valueOf(req.getParameter("id"));
            if (id == 0) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado ou inválido.");
            }

            // Retrieve and validate payment
            Payment payment = PaymentDAO.select(id);
            if (payment == null) {
                // Payment not found
                System.err.println("[WARN] Payment is null");
                req.setAttribute("errorMessage", "Pagamento não encontrado.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Forward to update page with payment
            req.setAttribute("payment", payment);
            req.getRequestDispatcher("/html/crud/payment/update.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // Handle invalid parameters
            System.err.println("[ERROR] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // Handle servlet dispatch errors
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/render-update");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
