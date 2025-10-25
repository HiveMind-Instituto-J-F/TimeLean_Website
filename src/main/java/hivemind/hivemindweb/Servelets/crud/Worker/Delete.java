package hivemind.hivemindweb.Servelets.crud.Worker;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/worker/delete")
public class Delete extends HttpServlet {
    @Override
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Worker/Delete.java
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve and validate the CPF parameter
        String cpf = request.getParameter("cpf");
        if (cpf == null || cpf.isEmpty()) {
            System.err.println("[WARN] Missing CPF parameter.");
            request.setAttribute("errorMessage", "Missing CPF parameter.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

=======
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Worker/Delete.java
        try {
            // [VALIDATION] Get and validate CPF parameter
            String paramCpf = req.getParameter("cpf");
            if (paramCpf == null || paramCpf.isEmpty()) {
                throw new IllegalArgumentException("CPF parameter is missing");
            }

<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Worker/Delete.java
            // Handle case where deletion did not occur (e.g., worker not found)
            System.err.println("[ERROR] Worker not found or could not be deleted.");
            request.setAttribute("errorMessage", "Unable to delete the worker. Please verify the CPF and try again.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        } catch (NullPointerException npe) {
            // Handle null references (e.g., DAO returned null or invalid worker)
            System.err.println("[WORKER-DELETE] Null reference encountered: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while processing worker deletion.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
=======
            // [DATA ACCESS] Attempt to delete worker
            boolean deleted = WorkerDAO.delete(new Worker(paramCpf));
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Worker/Delete.java

            if (deleted) {
                // [SUCCESS LOG] Worker deleted successfully
                System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Worker deleted: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Worker not found or deletion failed
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Worker not found or could not be deleted: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível deletar o trabalhador. Verifique o CPF e tente novamente.");
                req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameter
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "CPF inválido ou parâmetro ausente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao processar a exclusão do trabalhador.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalStateException ise) {
            // [FAILURE LOG] Session or response error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalStateException: " + ise.getMessage());
            req.setAttribute("errorMessage", "Erro de sessão ou resposta. Recarregue a página e tente novamente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
