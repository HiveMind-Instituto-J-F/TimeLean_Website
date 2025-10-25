package hivemind.hivemindweb.Servelts.crud.Plant.update;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/plant/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [PROCESS] Handle plant update submission
        try {
            // [VALIDATION] Retrieve and validate parameters
            String cnpjParam = req.getParameter("CNPJ");
            String cnaeParam = req.getParameter("CNAE");
            String responsibleCpfParam = req.getParameter("RESPONSIBLE_CPF");
            boolean operationalStatusParam = Boolean.parseBoolean(req.getParameter("OPERATIONAL_STATUS"));
            String addressCepParam = req.getParameter("ADDRESS_CEP");
            int addressNumberParam = Integer.parseInt(req.getParameter("ADDRESS_NUMBER"));
            String cnpjCompanyParam = req.getParameter("CNPJ_COMPANY");

            Plant plantLocal = new Plant(cnpjParam, cnaeParam, responsibleCpfParam,
                    operationalStatusParam, addressCepParam, addressNumberParam, cnpjCompanyParam);

            // [DATA ACCESS] Update Plant in database
            boolean updated = PlantDAO.update(plantLocal);
            if (updated) {
                System.err.println("[SUCCESS LOG] [" + cnpjParam + "] Plant updated successfully.");
                req.setAttribute("companyCnpj", cnpjCompanyParam);
                resp.sendRedirect(req.getContextPath() + "/plant/read");
            } else {
                throw new IllegalStateException("Falha ao atualizar a planta com CNPJ: " + cnpjParam);
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Handle validation or update failures
            System.err.println("[ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao atualizar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[FATAL] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao atualizar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
