package hivemind.hivemindweb.Servelts.crud.Worker;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Get and validate CPF parameter
            String paramCpf = req.getParameter("cpf");
            if (paramCpf == null || paramCpf.isEmpty()) {
                throw new IllegalArgumentException("CPF parameter is missing");
            }

            // [DATA ACCESS] Attempt to delete worker
            boolean deleted = WorkerDAO.delete(paramCpf);

            if (deleted) {
                // [SUCCESS LOG] Worker deleted successfully
                System.err.println("[INFO] [" + LocalDateTime.now() + "] Worker deleted: " + paramCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Worker not found or deletion failed
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Worker not found or could not be deleted: " + paramCpf);
                req.setAttribute("errorMessage", "Não foi possível deletar o trabalhador. Verifique o CPF e tente novamente.");
                req.getRequestDispatcher("/html/crud/Worker/delete.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "CPF inválido ou parâmetro ausente.");
            req.getRequestDispatcher("/html/crud/Worker/delete.jsp").forward(req, resp);
        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao processar a exclusão do trabalhador.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalStateException ise) {
            // [FAILURE LOG] Session or response error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalStateException: " + ise.getMessage());
            req.setAttribute("errorMessage", "Erro de sessão ou resposta. Recarregue a página e tente novamente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
