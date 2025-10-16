package hivemind.hivemindweb.Servelts.Plant;


import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/company-cnpj")
public class Read extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameter
        String cnpj = request.getParameter("company-cnpj");

        // List of plants that belongs to the company
        List<Plant> plantList = PlantDAO.select(cnpj);
        request.setAttribute("cnpj_company", cnpj);
        request.setAttribute("plantList", plantList);
        request.getRequestDispatcher("html/crud/plant/read.jsp").forward(request, response);
    }
}
