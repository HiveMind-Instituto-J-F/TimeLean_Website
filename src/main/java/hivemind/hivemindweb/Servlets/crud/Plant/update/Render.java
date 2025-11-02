package hivemind.hivemindweb.Servlets.crud.Plant.update;

import java.io.IOException;
import java.time.LocalDateTime;

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
                throw new IllegalArgumentException("Valor nulo ou vazio: 'cnpj'");
            }

            // [DATA ACCESS] Retrieve Plant by CNPJ
            Plant plantLocal = PlantDAO.selectByPlantCnpj(cnpjParam);
            if (plantLocal == null) {
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Planta não encontrada para o CNPJ: " + cnpjParam);
                req.setAttribute("errorMessage", "Planta não encontrada para o CNPJ informado.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                return;
            }

            // [SUCCESS LOG] Log success Forward Plant data to update JSP
            req.setAttribute("plant", plantLocal);
            req.getRequestDispatcher("/pages/crud/plant/update.jsp").forward(req, resp);
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Planta carregada com sucesso para atualização: " + cnpjParam);

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Handle invalid 'cnpj' parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar CNPJ: " + iae.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exceptions
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a planta: referência nula encontrada.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (ServletException | IOException e) {
            // [FAILURE LOG] Handle servlet forwarding or IO errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao carregar a página de atualização da planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Erro inesperado: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao processar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
