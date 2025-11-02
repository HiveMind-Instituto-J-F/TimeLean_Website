package hivemind.hivemindweb.Servlets.crud.Worker.Update;

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
            String cpf = req.getParameter("cpf");
            if (cpf == null || cpf.isEmpty()) {
                throw new IllegalArgumentException("Null value: 'cpf'");
            }

            // [DATA ACCESS] Retrieve worker by CPF
            Worker worker = WorkerDAO.selectByCpf(cpf);
            if (worker == null) {
                throw new NullPointerException("Worker not found for CPF: " + cpf);
            }

            // [PROCESS] Set worker attribute and forward to update page
            req.setAttribute("worker", worker);
            req.getRequestDispatcher("/WEB-INF/view/crud/worker/update.jsp").forward(req, resp);

            // [SUCCESS LOG] Worker render successful
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Worker render successful for CPF: " + cpf);

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Parameter validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + ia.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Worker not found
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Trabalhador não encontrado.");
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Dispatcher error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/worker/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
