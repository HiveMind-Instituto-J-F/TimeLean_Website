package hivemind.hivemindweb.Servelts.Worker.Update;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/worker/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the current HTTP session
        HttpSession session = request.getSession();

        // Get worker data from form submission
        String cpf = request.getParameter("cpf");
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String sector = request.getParameter("sector");
        String loginEmail = request.getParameter("loginEmail");
        String loginPassword = request.getParameter("loginPassword");
        String plantCnpj = (String) session.getAttribute("plantCnpj");

        Worker worker = null;

        try {
            // Attempt to create a Worker object with the provided data
            worker = new Worker(
                    cpf,
                    role,
                    sector,
                    name,
                    loginEmail,
                    loginPassword,
                    plantCnpj
            );
        } catch (NullPointerException npe) {
            // Handle missing required fields
            System.err.println("[WORKER-UPDATE] Missing required data: " + npe.getMessage());
            request.setAttribute("errorMessage", "Required fields were not provided.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException iae) {
            // Handle invalid field values (e.g., format or validation errors)
            System.err.println("[WORKER-UPDATE] Invalid data: " + iae.getMessage());
            request.setAttribute("errorMessage", "The provided data is invalid. Please check and try again.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        try {
            // Attempt to update worker information in the database
            boolean updated = WorkerDAO.update(worker);

            if (updated) {
                // Redirect to the worker list if the update succeeds
                response.sendRedirect(request.getContextPath() + "/worker/read");
            } else {
                // Handle database update failure
                System.err.println("[WORKER-UPDATE] Failed to update worker in the database.");
                request.setAttribute("errorMessage", "Failed to update the worker.");
                request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            }
        } catch (NullPointerException npe) {
            // Handle null objects or missing attributes during the update
            System.err.println("[WORKER-UPDATE] Worker or attributes are null: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while processing worker data.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        } catch (IllegalStateException ise) {
            // Handle session or response errors (e.g., invalid forwarding or closed response)
            System.err.println("[WORKER-UPDATE] Session or response state error: " + ise.getMessage());
            request.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        }
    }
}
