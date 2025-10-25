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
            FilterType filterType = FilterType.INPUT_OPTION;

            if (paramFilterCompanyName == null || paramFilterCompanyName.isEmpty()) {
                paramFilterCompanyName = null;
            }

            String filterValue = "active-plants";
            if (paramFilterCompanyName == null && paramChosenFilter != null) {
                switch (paramChosenFilter.toLowerCase()) {
                    case "active-plants", "inactive-plants", "all-plants" -> filterValue = paramChosenFilter;
                    default -> {
                        System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Invalid filter: " + paramChosenFilter);
                        req.setAttribute("errorMessage", "Filtro inválido fornecido.");
                        req.setAttribute("errorUrl", "/html/toUser.html");
                        req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                        return;
                    }
                }
            } else if (paramFilterCompanyName != null) {
                filterType = FilterType.INPUT_TEXT;
                filterValue = paramFilterCompanyName;
            }

            // [DATA ACCESS] Retrieve filtered list of plants
            List<Plant> plantList = PlantDAO.selectFilter(filterValue, filterType);
            req.setAttribute("plantList", plantList);

            // [SUCCESS LOG] Forward to JSP
            req.getRequestDispatcher("/html/crud/plant/read.jsp").forward(req, resp);
            System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Plants read successfully. Total: " + (plantList != null ? plantList.size() : 0));

        } catch (Exception e) {
            // [FAILURE LOG] Handle unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error in Plant.Read: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao carregar plantas: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/toUser.html");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
