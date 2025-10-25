package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import hivemind.hivemindweb.DAO.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [PROCESS] Handle deletion of a payment
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Parameter 'id' not informed.");
            }
            int id = Integer.parseInt(idParam);

            // [LOGIC] Check payment status before deletion
            try {
                if (!PaymentDAO.select(id).getStatus().equalsIgnoreCase("paid")) {
                    if (PaymentDAO.delete(id)) {
                        // [SUCCESS LOG] Payment deleted successfully
                        System.err.println("[SUCCESS] Payment deleted successfully, id: " + id);
                        req.setAttribute("msg", "Pagamento foi deletado com sucesso!");
                    } else {
                        // [FAILURE LOG] Failed DB deletion
                        System.err.println("[FAILURE] Failed to delete payment in DB, id: " + id);
                        req.setAttribute("errorMessage", "Pagamento não foi deletado devido a um erro no banco de dados.");
                        req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                } else {
                    // [FAILURE LOG] Payment already paid
                    System.err.println("[WARN] Attempt to delete an already paid payment, id: " + id);
                    req.setAttribute("errorMessage", "Pagamento não pode ser deletado porque já foi pago.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // [FAILURE LOG] Payment not found
                System.err.println("[WARN] Payment not found, id: " + id + ", Error: " + npe.getMessage());
                req.setAttribute("errorMessage", "Pagamento não encontrado ou inválido.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input
            System.err.println("[FAILURE] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao deletar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
