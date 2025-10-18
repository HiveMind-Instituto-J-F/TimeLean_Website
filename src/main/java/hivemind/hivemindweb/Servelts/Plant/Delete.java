<<<<<<< HEAD
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
=======
package hivemind.hivemindweb.Servelts.Plant;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/plant/delete")
public class Delete extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String plant_cnpj = request.getParameter("cnpj");

        Plant plant = new Plant(plant_cnpj);
        plant.setOperationalStatus(false);
        PlantDAO.switchOperationalStatus(plant);
        response.sendRedirect(request.getContextPath() + "/plant/read");
    }
}
/*
 * BUSINESS RULES (DO NOT DELETE)
 * In order to rollback, the system must verify if company is active.
 * Workers must not be deactivated/deleted after a plant deactivation/deletion.
 */
>>>>>>> e734a8c3b8b66ef783d4380e71a773d3c00489ee
