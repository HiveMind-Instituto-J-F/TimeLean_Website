package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Plant;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.Filter;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String plantCnpj = (String) session.getAttribute("plantCnpj");

            if (plantCnpj == null || plantCnpj.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");
            }

            Plant plant = PlantDAO.selectByPlantCnpj(plantCnpj);
            if (plant == null) {
                throw new IllegalArgumentException("CNPJ cant take in DB");
            }

            List<Worker> workers = WorkerDAO.selectByPlantCnpj(plant.getCNPJ());

            request.setAttribute("workers", workers);
            request.setAttribute("plantCnpj", plantCnpj);

            request.getRequestDispatcher("/html/crud/worker/read.jsp").forward(request, response);

        } catch (NullPointerException npe) {
            System.err.println("[WARN] Null reference encountered: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while retrieving plant or worker data.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        } catch (IllegalStateException ise) {
            System.err.println("[WARN] Illegal state error: " + ise.getMessage());
            request.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException iae) {
            System.err.println("[WARN] Invalid argument: " + iae.getMessage());
            request.setAttribute("errorMessage", "Invalid plant data. Please verify the session information.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }
    }
}
