package hivemind.hivemindweb.Servelts.crud.Worker.Update;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/worker/render-update")
public class Render extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Get and validate parameter
            String paramCpf = req.getParameter("cpf");
            if (paramCpf == null || paramCpf.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");
            }

            // [DATA ACCESS] Retrieve worker by CPF
            Worker worker = WorkerDAO.selectByCpf(paramCpf);
            if (worker == null) {
                throw new NullPointerException("Worker not found for CPF: " + paramCpf);
            }

            // [PROCESS] Set worker attribute and forward to update page
            req.setAttribute("worker", worker);
            req.getRequestDispatcher("/html/crud/worker/update.jsp").forward(req, resp);

            System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Worker render successful for CPF: " + paramCpf);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Worker not found
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Trabalhador não encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
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
