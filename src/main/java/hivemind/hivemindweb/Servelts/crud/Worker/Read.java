package hivemind.hivemindweb.Servelts.crud.Worker;

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
            String plantCnpj = (String) session.getAttribute("plantCnpj");
            if (plantCnpj == null || plantCnpj.isEmpty()) {
                throw new IllegalArgumentException("Missing or empty plantCnpj in session");
            }

            // [LOGIC] Determine filter type based on request parameters
            FilterType filterType = FilterType.INPUT_TEXT;
            String filter = null;

            String paramCpfFilter = req.getParameter("cpfFilter");
            String paramSectorFilter = req.getParameter("sectorFilter");

            if (paramCpfFilter != null && !paramCpfFilter.isEmpty()) {
                filterType = FilterType.INPUT_CPF;
                filter = paramCpfFilter;
            } else if (paramSectorFilter != null && !paramSectorFilter.isEmpty()) {
                filterType = FilterType.INPUT_SECTOR;
                filter = paramSectorFilter;
            }

            // [DATA ACCESS] Retrieve workers with applied filter
            List<Worker> workers = WorkerDAO.selectFilter(filterType, filter, plantCnpj);

            // [PROCESS] Set attributes and forward to JSP
            req.setAttribute("workers", workers);
            req.setAttribute("plantCnpj", plantCnpj);
            req.getRequestDispatcher("/html/crud/worker/read.jsp").forward(req, resp);

            // [SUCCESS LOG] Workers retrieved successfully
            System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Workers retrieved for plant: " + plantCnpj);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Missing or invalid plantCnpj
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos da planta. Verifique as informações da sessão.");
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null reference encountered in DAO
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao recuperar dados de trabalhadores ou planta.");
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IllegalStateException ise) {
            // [FAILURE LOG] Session or response error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] IllegalStateException: " + ise.getMessage());
            req.setAttribute("errorMessage", "Erro de sessão ou resposta. Recarregue a página.");
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected error
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Unexpected exception: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
