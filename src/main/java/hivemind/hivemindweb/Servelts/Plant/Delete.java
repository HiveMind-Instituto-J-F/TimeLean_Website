package hivemind.hivemindweb.Servelts.Plant;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-plant")
public class Delete extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String plant_cnpj = request.getParameter("cnpj");
        PlantDAO.delete(new Plant(plant_cnpj));
        response.sendRedirect("html/crud/plant/read.jsp");
    }
}
