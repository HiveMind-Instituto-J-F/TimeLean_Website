package hivemind.hivemindweb.Servlets.crud.Worker;

import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/worker/read")
public class Read extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Retrieve the current session and plantCnpj
            HttpSession session = req.getSession();
            String plantCnpjParam = (String) session.getAttribute("plantCnpj");
            if (plantCnpjParam == null || plantCnpjParam.isEmpty()) {
                throw new IllegalArgumentException("Missing or empty plantCnpj in session");
            }

            // [LOGIC] Determine filter type based on request parameters
            FilterType.Worker filterType = FilterType.Worker.ALL_VALUES;
            String filter = null;

            String cpfFilter = req.getParameter("cpfFilter");
            String sectorFilter = req.getParameter("sectorFilter");

            if (cpfFilter != null && !cpfFilter.isEmpty()) {
                filterType = FilterType.Worker.CPF;
                filter = cpfFilter;
            } else if (sectorFilter != null && !sectorFilter.isEmpty()) {
                filterType = FilterType.Worker.SECTOR;
                filter = sectorFilter;
            }

            // [DATA ACCESS] Retrieve workers with applied filter
            List<Worker> workers = WorkerDAO.selectFilter(filterType, filter, plantCnpjParam);

            // [PROCESS] Set attributes and forward to JSP
            req.setAttribute("workers", workers);
            req.setAttribute("plantCnpj", plantCnpjParam);
            req.getRequestDispatcher("/WEB-INF/view/crud/worker/read.jsp").forward(req, resp);

            // [SUCCESS LOG] Workers retrieved successfully
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Workers retrieved for plant: " + plantCnpjParam);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Missing or invalid plantCnpj
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos da planta. Verifique as informações da sessão.");
            req.setAttribute("errorUrl", "/pages/chooser.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered in DAO
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao recuperar dados de trabalhadores ou planta.");
            req.setAttribute("errorUrl", "/pages/chooser.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalStateException ise) {
            // [FAILURE LOG] Session or response error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalStateException: " + ise.getMessage());
            req.setAttribute("errorMessage", "Erro de sessão ou resposta. Recarregue a página.");
            req.setAttribute("errorUrl", "/pages/chooser.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/pages/chooser.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
