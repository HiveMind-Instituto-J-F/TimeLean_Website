package hivemind.hivemindweb.Servelts.crud.Plant.update;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plant/render-update")
public class Render extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle rendering of Plant update page
        try {
            // [VALIDATION] Get and validate 'cnpj' parameter
            String cnpjParam = req.getParameter("cnpj");
            if (cnpjParam == null || cnpjParam.isEmpty()) {
                throw new IllegalArgumentException("Null value: 'cnpj'");
            }

            // [DATA ACCESS] Retrieve Plant by CNPJ
            Plant plantLocal = PlantDAO.selectByPlantCnpj(cnpjParam);
            if (plantLocal == null) {
                System.err.println("[ERROR] [" + cnpjParam + "] Plant not found.");
                req.setAttribute("errorMessage", "Planta não encontrada para o CNPJ informado.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Forward Plant data to update JSP
            req.setAttribute("plant", plantLocal);
            req.getRequestDispatcher("/html/crud/plant/update.jsp").forward(req, resp);
            System.err.println("[INFO] [" + cnpjParam + "] Plant loaded successfully for update.");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Handle invalid 'cnpj' parameter
            System.err.println("[ERROR] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar CNPJ: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException | IOException e) {
            // [FAILURE LOG] Handle servlet forwarding or IO errors
            System.err.println("[ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao carregar a página de atualização da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao processar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
