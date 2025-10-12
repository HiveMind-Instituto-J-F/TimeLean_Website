package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/worker/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve worker data from the form
        String cpf = request.getParameter("cpf");
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String sector = request.getParameter("sector");
        String loginEmail = request.getParameter("loginEmail");
        String loginPassword = AuthService.hash(request.getParameter("loginPassword"));

        // Get the current session (does not create a new one if absent)
        HttpSession session = request.getSession(false);

        if (session == null) {
            // Handle invalid or expired session
            System.err.println("[WORKER-CREATE] Session not found or expired.");
            request.setAttribute("errorMessage", "Session expired. Please log in again.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        String plantCnpj = (String) session.getAttribute("plantCnpj");

        Worker worker;
        try {
            // Attempt to create a Worker object with validated data
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
            // Handle missing required information
            System.err.println("[WORKER-CREATE] Missing required data: " + npe.getMessage());
            request.setAttribute("errorMessage", "Some required fields are missing. Please fill all fields.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException iae) {
            // Handle invalid data formats or constraints
            System.err.println("[WORKER-CREATE] Invalid data: " + iae.getMessage());
            request.setAttribute("errorMessage", "Invalid input. Please verify the data and try again.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        try {
            // Try to insert the worker into the database
            boolean inserted = WorkerDAO.insert(worker);

            if (inserted) {
                // Redirect to list page if insertion is successful
                response.sendRedirect(request.getContextPath() + "/worker/read");
            } else {
                // Handle insertion failure (e.g., duplicate CPF)
                System.err.println("[WORKER-CREATE] Failed to insert worker in the database.");
                request.setAttribute("errorMessage", "Unable to create worker. Please try again later.");
                request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            }
        } catch (NullPointerException npe) {
            // Handle null references during insertion (e.g., DAO returned null)
            System.err.println("[WORKER-CREATE] Worker or attributes are null: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while saving worker data.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        } catch (IllegalStateException ise) {
            // Handle invalid response or session state
            System.err.println("[WORKER-CREATE] Session or response state error: " + ise.getMessage());
            request.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        }
    }
}
