<<<<<<< HEAD
package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/worker/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve and validate the CPF parameter
        String cpf = request.getParameter("cpf");
        if (cpf == null || cpf.isEmpty()) {
            System.err.println("[WORKER-DELETE] Missing CPF parameter.");
            request.setAttribute("errorMessage", "Missing CPF parameter.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
            return;
        }

        try {
            // Attempt to delete the worker from the database
            boolean deleted = WorkerDAO.delete(new Worker(cpf));

            if (deleted) {
                // Redirect to the list page if deletion succeeded
                response.sendRedirect(request.getContextPath() + "/worker/read");
                return;
            }

            // Handle case where deletion did not occur (e.g., worker not found)
            System.err.println("[WORKER-DELETE] Worker not found or could not be deleted.");
            request.setAttribute("errorMessage", "Unable to delete the worker. Please verify the CPF and try again.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        } catch (NullPointerException npe) {
            // Handle null references (e.g., DAO returned null or invalid worker)
            System.err.println("[WORKER-DELETE] Null reference encountered: " + npe.getMessage());
            request.setAttribute("errorMessage", "Internal error while processing worker deletion.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);

        } catch (IllegalStateException ise) {
            // Handle session or response errors
            System.err.println("[WORKER-DELETE] Illegal state: " + ise.getMessage());
            request.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);

        } catch (IllegalArgumentException iae) {
            // Handle invalid CPF or input data
            System.err.println("[WORKER-DELETE] Invalid data: " + iae.getMessage());
            request.setAttribute("errorMessage", "Invalid CPF format or data provided.");
            request.getRequestDispatcher("/html/crud/worker/error/error.jsp").forward(request, response);
        }
    }
}
=======
package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/worker/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Retrieve and validate the CPF parameter
            String cpf = req.getParameter("cpf");
            if(cpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'cpf'");}

            // Attempt to delete the worker from the database
            if (WorkerDAO.delete(new Worker(cpf))) {
                // Redirect to the list page if deletion succeeded
                resp.sendRedirect(req.getContextPath() + "\\worker\\read");
                return;
            }
            
            // Handle case where deletion did not occur (e.g., worker not found)
            System.err.println("[WARN] Worker not found or could not be deleted.");
            req.setAttribute("errorMessage", "Unable to delete the worker. Please verify the CPF and try again.");
            req.getRequestDispatcher("\\html\\crud\\worker\\error\\error.jsp").forward(req, resp);
        } catch (NullPointerException npe) {
            // Handle null references (e.g., DAO returned null or invalid worker)
            System.err.println("[WARN] Null reference encountered: " + npe.getMessage());
            req.setAttribute("errorMessage", "Internal error while processing worker deletion.");
            req.getRequestDispatcher("\\html\\crud\\worker\\error\\error.jsp").forward(req, resp);
        } catch (IllegalStateException ise) {
            // Handle session or response errors
            System.err.println("[WARN] Illegal state: " + ise.getMessage());
            req.setAttribute("errorMessage", "Session or response error. Please reload the page.");
            req.getRequestDispatcher("\\html\\crud\\worker\\error\\error.jsp").forward(req, resp);

        } catch (IllegalArgumentException iae) {
            // Handle invalid CPF or input data
            System.err.println("[WARN] Invalid data: " + iae.getMessage());
            req.setAttribute("errorMessage", "Invalid CPF format or data provided.");
            req.getRequestDispatcher("\\html\\crud\\worker\\error/error.jsp").forward(req, resp);
        }
    }
}
>>>>>>> refs/remotes/origin/dev
