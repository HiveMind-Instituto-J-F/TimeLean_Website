package hivemind.hivemindweb.Servlets.crud.Plant;

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
                throw new IllegalArgumentException("Valor nulo ou vazio: 'cnpj'");
            }

            // [DATA ACCESS] Retrieve Plant and Company
            Plant plantFromDb = PlantDAO.selectByPlantCnpj(cnpjParam);
            if (plantFromDb == null) {
                throw new NullPointerException("Planta não encontrada. CNPJ: " + cnpjParam);
            }

            Company companyLocal = CompanyDAO.select(plantFromDb.getCnpjCompany());
            if (companyLocal == null) {
                throw new NullPointerException("Empresa vinculada à planta não encontrada: " + cnpjParam);
            }

            // [LOGIC] Verify if company is active and perform rollback
            if (companyLocal.isActive()) {
                plantFromDb.setOperationalStatus(true);
                if (PlantDAO.update(plantFromDb)) {
                    // [SUCCESS LOG] Rollback plant delete successfully
                    System.out.println("[INFO] [" + LocalDateTime.now() + "] Exclusão da planta revertida com sucesso: " + cnpjParam);
                    resp.sendRedirect(req.getContextPath() + "/plant/read");
                } else {
                    throw new IllegalStateException("Falha ao tentar reativar a planta: " + cnpjParam);
                }
            } else {
                throw new IllegalStateException("A planta já está desativada.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Handle expected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao reverter exclusão da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback -> NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro ao reverter exclusão da planta: referência nula encontrada.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] DeleteRollback unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao reverter exclusão da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
