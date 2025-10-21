package hivemind.hivemindweb.Servelts.Plant.update;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/plant/read")
public class Read extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameter
        String chosenFilter = request.getParameter("filter");
        String filterCompanyName = request.getParameter("filterCompanyName");
        FilterType filterType = FilterType.INPUT_OPTION;

        try{
            if (filterCompanyName.isEmpty()) filterCompanyName = null;
        } catch (NullPointerException npe){
            filterCompanyName = null;
        }


        String filter = "active-plants";
        if (filterCompanyName == null && chosenFilter != null){
            if ("active-plants".equalsIgnoreCase(chosenFilter)){
                filter = chosenFilter;
            } else if ("inactive-plants".equalsIgnoreCase(chosenFilter)) {
                filter = chosenFilter;
            } else if (chosenFilter.equals("all-plants")) {
                filter = chosenFilter;
            } else {
                System.err.println("[PLANT-READ] Invalid filter.");
                request.setAttribute("errorMessage", "Invalid filter.");
                request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
                return;
            }
        } else if (filterCompanyName != null){
            filterType = FilterType.INPUT_TEXT;
            filter = filterCompanyName;
        }

        // List of plants
        List<Plant> plantList = PlantDAO.selectFilter(filter, filterType);
        request.setAttribute("plantList", plantList);
        request.getRequestDispatcher("/html/crud/plant/read.jsp").forward(request, response);
    }
}
/*
 * BUSINESS RULES (DO NOT DELETE)
 * In order to rollback, the system must verify if company is active.
 * Workers must not be deactivated/deleted after a plant deactivation/deletion.
 */
