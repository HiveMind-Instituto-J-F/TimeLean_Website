package hivemind.hivemindweb.Servelts.crud.Plant;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/plant/delete/rollback")
public class DeleteRollback extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle rollback of plant deletion
        try {
            // [VALIDATION] Get and validate CNPJ parameter
            String cnpjParam = req.getParameter("cnpj");
            if (cnpjParam == null || cnpjParam.isEmpty()) {
                throw new IllegalArgumentException("Null value: 'cnpj'");
            }

            // [DATA ACCESS] Retrieve Plant and Company
            Plant plantFromDb = PlantDAO.selectByPlantCnpj(cnpjParam);
            if (plantFromDb == null) {
                throw new NullPointerException("Plant not found. CNPJ: " + cnpjParam);
            }

            Company companyLocal = CompanyDAO.select(plantFromDb.getCnpjCompany());
            if (companyLocal == null) {
                throw new NullPointerException("Company linked to plant: not found: " + cnpjParam);
            }

            // [LOGIC] Verify if company is active and perform rollback
            if (companyLocal.isActive()) {
                plantFromDb.setOperationalStatus(true);
                if (PlantDAO.update(plantFromDb)) {
                    System.err.println("[INFO] [" + LocalDateTime.now() + "] Rollback plant delete successfully: " + cnpjParam);
                    resp.sendRedirect(req.getContextPath() + "/plant/read");
                } else {
                    throw new IllegalStateException("Failed while trying to reactive the plant: " + cnpjParam);
                }
            } else {
                throw new IllegalStateException("The plant is already deactivated.");
            }

        } catch (IllegalArgumentException | NullPointerException | IllegalStateException e) {
            // [FAILURE LOG] Handle expected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao reverter exclusão da planta: " + e.getMessage());
            req.getRequestDispatcher("/html/crud/plant/delete.jsp").forward(req, resp);
        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao reverter exclusão da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
