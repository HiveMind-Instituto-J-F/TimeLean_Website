package hivemind.hivemindweb.Servelts.crud.Payment.update;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [PROCESS] Handle rendering of payment update page
        try {
            String idParam = req.getParameter("id");

            // [VALIDATION] Ensure id parameter is valid
            if (idParam == null || idParam.isEmpty() || Integer.parseInt(idParam) == 0) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado ou inválido.");
            }

            int id = Integer.parseInt(idParam);

            // [DATA ACCESS] Retrieve payment from database
            Payment payment = PaymentDAO.select(id);
            if (payment == null) {
                // [FAILURE LOG] Payment not found
                System.err.println("[FAILURE] Payment not found, id: " + id);
                req.setAttribute("errorMessage", "Pagamento não encontrado.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [SUCCESS LOG] Payment retrieved successfully
            System.err.println("[SUCCESS] Payment loaded successfully, id: " + id);
            req.setAttribute("payment", payment);
            req.getRequestDispatcher("/html/crud/payment/update.jsp").forward(req, resp);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid parameter input
            System.err.println("[FAILURE] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Erro nos parâmetros informados: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch exception
            System.err.println("[FAILURE] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE] Exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao carregar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
