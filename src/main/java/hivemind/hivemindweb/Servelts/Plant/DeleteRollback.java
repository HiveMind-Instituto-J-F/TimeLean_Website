package hivemind.hivemindweb.Servelts.Plant;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/plant/delete/rollback")
public class DeleteRollback extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get attributes
        String cnpj = request.getParameter("cnpj");

        if (cnpj == null){
            System.err.println("[PLANT-DELETE-ROLLBACK] cnpj is null");
            request.setAttribute("errorMessage", "Unable to delete: cnpj is null)");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        }

        // Verify if company is active:
        Plant plant = PlantDAO.selectByPlantCnpj(cnpj); // Quitto, coloca try-catch aq pq tou com preguica e esse é teu papel:)
        Company comapany = CompanyDAO.select(plant.getCnpjCompany());


        try {
            if (comapany.isActive()){
                plant.setOperationalStatus(true);
                if (PlantDAO.switchOperationalStatus(plant)){
                    System.out.println("[PLANT-DELETE-ROLLBACK] Rollback: Rollback plant delete.");
                    response.sendRedirect(request.getContextPath() + "/plant/read");
                    return;

                }

            }

            System.err.println("[PLANT-DELETE-ROLLBACK] ERROR: Company is deactivated.");
            request.setAttribute("errorMessage", "Company is deactivated.");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        } catch (NullPointerException npe){
            System.err.println("[PLANT-DELETE-ROLLBACK] NullPointerException exception.");
            request.setAttribute("errorMessage", "Unable to delete: NullPointerException");
            request.getRequestDispatcher("/html/error/error.jsp").forward(request, response);
        }
    }
}
/*
* BUSINESS RULES (DO NOT DELETE)
* In order to rollback, the system must verify if company is active.
* Workers must not be deactivated/deleted after a plant deactivation/deletion.
*/