package hivemind.hivemindweb.Servelts.crud.Plant;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/plant/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle plant deletion by switching operational status to false
        try {
            // [VALIDATION] Get and validate CNPJ parameter
            String cnpjParam = req.getParameter("cnpj");
            if (cnpjParam == null || cnpjParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'cnpj' não informado.");
            }

            // [DATA ACCESS] Select plant and switch operational status
            Plant plantFromDb = PlantDAO.selectByPlantCnpj(cnpjParam);
            plantFromDb.setOperationalStatus(false);
            PlantDAO.update(plantFromDb);

            System.err.println("[INFO] [" + LocalDateTime.now() + "] Plant operational status set to false successfully: " + cnpjParam);
            resp.sendRedirect(req.getContextPath() + "/plant/read");

        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Handle missing or invalid parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao desativar a planta: " + e.getMessage());
            req.getRequestDispatcher("/html/crud/plant/delete.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[INFO] [" + LocalDateTime.now() + "] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao desativar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
