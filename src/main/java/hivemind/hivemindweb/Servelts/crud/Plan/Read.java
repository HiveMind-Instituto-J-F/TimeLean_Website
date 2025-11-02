package hivemind.hivemindweb.Servelts.crud.Plan;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/read")
public class Read extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [DATA ACCESS] Retrieve list of plans from database
            List<Plan> planList = PlanDAO.select();

            // [SUCCESS LOG] Log successful retrieval of plan list
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Plan list successfully loaded. Total: " + planList.size());

            // [PROCESS] Set attribute and forward to JSP for rendering
            req.setAttribute("plans", planList);
            req.getRequestDispatcher("/html/crud/plan/read.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Handle servlet dispatching exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException occurred: " + se.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro interno no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch any other unexpected exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception occurred: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
