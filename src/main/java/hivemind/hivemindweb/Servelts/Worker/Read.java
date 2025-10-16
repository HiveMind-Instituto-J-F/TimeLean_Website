package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Plant;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/worker/read")
public class Read extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the current session (creates one if not existing)
        HttpSession session = request.getSession();

        // Get the plant CNPJ stored in the session
        String plantCnpj = (String) session.getAttribute("plantCnpj");

        if (plantCnpj == null || plantCnpj.isEmpty()) {
            // Handle missing or invalid session attribute
            System.err.println("[WORKER-READ] Missing plantCnpj in session.");
            request.setAttribute("errorMessage", "Plant information not found in session.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        try {
            // Retrieve plant data from the database
            Plant plant = PlantDAO.selectByPlantCnpj(plantCnpj);

            if (plant == null) {
                // Handle non-existent plant in the database
                System.err.println("[WORKER-READ] No plant found with CNPJ: " + plantCnpj);
                request.setAttribute("errorMessage", "No plant found with CNPJ: " + plantCnpj);
                request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
                return;
            }

            // Retrieve all workers associated with the plant
            List<Worker> workers = WorkerDAO.selectByPlantCnpj(plant.getCNPJ());
            System.out.println(workers);

            // Set attributes for the JSP view
            request.setAttribute("workers", workers);
            request.setAttribute("plantCnpj", plantCnpj);

            // Forward to the JSP page for display
            request.getRequestDispatcher("/html/crud/worker/read.jsp").forward(request, response);
        } catch (NullPointerException npe) {
            // Handle null references (e.g., DAO returned null unexpectedly)
            System.err.println("[WORKER-READ] Null reference encountered: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while retrieving plant or worker data.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);

        } catch (IllegalStateException ise) {
            // Handle session or response errors
            System.err.println("[WORKER-READ] Illegal state error: " + ise.getMessage());
            request.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);

        } catch (IllegalArgumentException iae) {
            // Handle invalid plantCnpj or DAO argument
            System.err.println("[WORKER-READ] Invalid argument: " + iae.getMessage());
            request.setAttribute("errorMessage", "Invalid plant data. Please verify the session information.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        }
    }
}