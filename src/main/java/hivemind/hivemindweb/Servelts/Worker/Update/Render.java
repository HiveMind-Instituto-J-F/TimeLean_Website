package hivemind.hivemindweb.Servelts.Worker.Update;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/render-update")
public class Render extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get and validate parameter
        String cpf = request.getParameter("cpf");
        if (cpf == null){
            System.out.println("[WORKER-UPDATE-RENDER] ERROR: cpf is null");
            response.sendRedirect("html\\crud\\worker\\error\\error.jsp");
            return;
        }

        // Create and validate worker
        Worker worker;
        try {
            worker = WorkerDAO.selectByCpf(cpf);
            if (worker == null){
                // Redirect to error.jsp in case of worker being null
                System.out.println("[WORKER-UPDATE-RENDER] ERROR: Worker is null");
                response.sendRedirect("html\\crud\\worker\\error\\error.jsp");
            }
        } catch (NullPointerException npe) {
            // Redirect to error.jsp in case of NullPointerException
            System.out.println("[WORKER-UPDATE-RENDER] ERROR: NullPointerException");
            response.sendRedirect("html\\crud\\worker\\error\\error.jsp");
            return;
        }

        // Render and dispatch worker
        request.setAttribute("worker", worker);
        request.getRequestDispatcher("html\\crud\\worker\\update.jsp").forward(request, response);
    }
}
