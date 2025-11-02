package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import java.time.LocalDate;

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
            // [VALIDATION] Get and validate parameter
            String idParam = req.getParameter("id");

            if (idParam == null || idParam.isEmpty()) throw new IllegalArgumentException("Null value: 'id'");

            int id = Integer.parseInt(idParam);

            // [LOGIC] Check payment status before deletion
            try {
                if (!PaymentDAO.select(id).getStatus().equalsIgnoreCase("pago")) {
                    if (PaymentDAO.delete(id)) {
                        // [SUCCESS LOG] Payment deleted successfully
                        System.out.println("[INFO] [" + LocalDate.now() + "] Payment deleted successfully, id: " + id);
                    } else {
                        // [FAILURE LOG] Failed DB deletion
                        System.err.println("[ERROR] [" + LocalDate.now() + "] Failed to delete payment in DB, id: " + id);
                        req.setAttribute("errorMessage", "Pagamento não foi deletado devido a um erro no banco de dados.");
                        req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                        return;
                    }

                } else {
                    // [FAILURE LOG] Payment already paid
                    System.err.println("[ERROR] [" + LocalDate.now() + "] Attempt to delete an already paid payment, id: " + id);
                    req.setAttribute("errorMessage", "Pagamento não pode ser deletado porque já foi pago.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
                    req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
                // [FAILURE LOG] Payment not found
                System.err.println("[ERROR] [" + LocalDate.now() + "] Payment not found, id: " + id + ", Error: " + npe.getMessage());
                req.setAttribute("errorMessage", "Pagamento não encontrado ou inválido.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid input
            System.err.println("[ERROR] [" + LocalDate.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos, por favor, preencha todos os campos corretamente. Erro: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null values
            System.err.println("[ERROR] [" + LocalDate.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Algum valor nulo foi inserido: " + npe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[ERROR] [" + LocalDate.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao deletar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
