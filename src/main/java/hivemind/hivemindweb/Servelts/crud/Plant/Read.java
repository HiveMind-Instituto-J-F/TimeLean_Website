package hivemind.hivemindweb.Servelts.crud.Plant;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/plant/read")
public class Read extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle reading and filtering plants
        try {
            // [VALIDATION] Get and validate parameters
            String paramChosenFilter = req.getParameter("filter");
            String paramFilterCompanyName = req.getParameter("filterCompanyName");
            FilterType.Plant filterType = FilterType.Plant.ACTIVE;
            String filterValue = null;

            if (paramFilterCompanyName != null && !paramFilterCompanyName.isEmpty()) {
                filterType = FilterType.Plant.COMPANY_NAME;
                filterValue = paramFilterCompanyName;
            } else if (paramChosenFilter != null) {
                switch (paramChosenFilter.toLowerCase()) {
                    case "active-plants" -> filterType = FilterType.Plant.ACTIVE;
                    case "inactive-plants" -> filterType = FilterType.Plant.INACTIVE;
                    case "all-plants" -> filterType = FilterType.Plant.ALL_VALUES;
                    default -> {
                        System.err.println("[ERROR] [" + LocalDateTime.now() + "] Invalid filter: " + paramChosenFilter); // [FAILURE LOG] invalid filter
                        req.setAttribute("errorMessage", "Filtro inválido fornecido.");
                        req.setAttribute("errorUrl", "/html/toUser.html");
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                }
            }

            // [DATA ACCESS] Retrieve filtered list of plants
            List<Plant> plantList = PlantDAO.selectFilter(filterValue, filterType);
            req.setAttribute("plantList", plantList);

            // [SUCCESS LOG] Forward to JSP
            req.getRequestDispatcher("/html/crud/plant/read.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Plants read successfully. Total: " + (plantList != null ? plantList.size() : 0)); // [SUCCESS LOG] read completed

        } catch (NullPointerException npe) {
            // [FAILURE LOG] null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException in Plant.Read: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: dado ausente ou nulo.");
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        } catch (Exception e) {
            // [FAILURE LOG] unexpected error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error in Plant.Read: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao carregar plantas: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
