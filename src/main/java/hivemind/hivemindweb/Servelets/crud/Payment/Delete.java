package hivemind.hivemindweb.Servelets.crud.Payment;

import java.io.IOException;
import hivemind.hivemindweb.DAO.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/delete")
public class Delete extends HttpServlet {
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get and validate 'id' parameter
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {throw new IllegalArgumentException("Parameter 'id' not informed.");}
            int id = Integer.parseInt(idStr);
=======
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java

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
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
                        // Backend log in English; UI message remains Portuguese
                        System.out.println("[INFO] Payment deleted successfully.");
                        req.setAttribute("msg", "Pagamento foi deletado com sucesso!");
                    } else {
                        System.err.println("[ERROR] Failed to delete payment in DB.");
=======
                        // [SUCCESS LOG] Payment deleted successfully
                        System.err.println("[SUCCESS] Payment deleted successfully, id: " + id);
                        req.setAttribute("msg", "Pagamento foi deletado com sucesso!");
                    } else {
                        // [FAILURE LOG] Failed DB deletion
                        System.err.println("[FAILURE] Failed to delete payment in DB, id: " + id);
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java
                        req.setAttribute("errorMessage", "Pagamento não foi deletado devido a um erro no banco de dados.");
                        req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                } else {
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
                    // Payment already paid
                    System.err.println("[WARN] Attempt to delete an already paid payment.");
=======
                    // [FAILURE LOG] Payment already paid
                    System.err.println("[WARN] Attempt to delete an already paid payment, id: " + id);
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java
                    req.setAttribute("errorMessage", "Pagamento não pode ser deletado porque já foi pago.");
                    req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            } catch (NullPointerException npe) {
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
                // Payment not found or null
                System.err.println("[WARN] Payment not found, Error: " +  npe.getMessage());
=======
                // [FAILURE LOG] Payment not found
                System.err.println("[WARN] Payment not found, id: " + id + ", Error: " + npe.getMessage());
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java
                req.setAttribute("errorMessage", "Pagamento não encontrado ou inválido.");
                req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete?id=" + id);
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException iae) {
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
            // Handle invalid input
            System.err.println("[ERROR] Invalid input: " + iae.getMessage());
=======
            // [FAILURE LOG] Invalid input
            System.err.println("[FAILURE] IllegalArgumentException: " + iae.getMessage());
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Payment/Delete.java
        } catch (DateTimeParseException dpe) {
            // Handle date parsing errors (for future use)
            System.err.println("[ERROR] Failed to convert date: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
=======
        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE] Unexpected error: " + e.getMessage());
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Payment/Delete.java
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao deletar o pagamento.");
            req.setAttribute("errorUrl", req.getContextPath() + "/payment/delete");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
