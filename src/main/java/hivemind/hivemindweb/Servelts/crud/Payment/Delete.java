package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/delete")
public class Delete extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get and validate 'id' parameter
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id' não informado.");
            }
            int id = Integer.parseInt(idStr);

            // Attempt to delete payment only if not paid
            try {
                if (!PaymentDAO.select(id).getStatus().equalsIgnoreCase("paid")) {
                    if (PaymentDAO.delete(id)) {
                        System.out.println("[INF] Pagamento deletado com sucesso.");
                        req.setAttribute("msg", "Pagamento foi deletado com sucesso!");
                    } else {
                        System.err.println("[WARN] Falha ao deletar pagamento no banco.");
                        req.setAttribute("errorMessage", "Pagamento não foi deletado devido a um erro no banco de dados.");
                        req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                } else {
                    // Payment already paid
                    System.err.println("[WARN] Tentativa de deletar pagamento já pago.");
                    req.setAttribute("errorMessage", "Pagamento não pode ser deletado porque já foi pago.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // Payment not found or null
                System.err.println("[WARN] Pagamento não encontrado.");
                req.setAttribute("errorMessage", "Pagamento não encontrado ou inválido.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException iae) {
            // Handle invalid input
            System.err.println("[ERROR] Entrada inválida: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // Handle date parsing errors (for future use)
            System.err.println("[ERROR] Falha ao converter data: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Erro inesperado: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao deletar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
