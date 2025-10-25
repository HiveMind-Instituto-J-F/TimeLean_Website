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
                throw new IllegalArgumentException("Parâmetro 'cnpj' não informado.");
            }

            // [DATA ACCESS] Retrieve Plant and Company
            Plant plantLocal = PlantDAO.selectByPlantCnpj(cnpjParam);
            if (plantLocal == null) {
                throw new NullPointerException("Planta não encontrada para CNPJ: " + cnpjParam);
            }

            Company companyLocal = CompanyDAO.select(plantLocal.getCnpjCompany());
            if (companyLocal == null) {
                throw new NullPointerException("Empresa associada não encontrada para a planta: " + cnpjParam);
            }

            // [LOGIC] Verify if company is active and perform rollback
            if (companyLocal.isActive()) {
                plantLocal.setOperationalStatus(true);
                if (PlantDAO.switchOperationalStatus(plantLocal)) {
                    System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Rollback plant delete successfully: " + cnpjParam);
                    resp.sendRedirect(req.getContextPath() + "/plant/read");
                    return;
                } else {
                    throw new IllegalStateException("Falha ao reativar a planta: " + cnpjParam);
                }
            } else {
                throw new IllegalStateException("A empresa associada está desativada.");
            }

        } catch (IllegalArgumentException | NullPointerException | IllegalStateException e) {
            // [FAILURE LOG] Handle expected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao reverter exclusão da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[FATAL] [" + LocalDateTime.now() + "] DeleteRollback unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao reverter exclusão da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
