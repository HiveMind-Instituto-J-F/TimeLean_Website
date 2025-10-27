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
            String operationalStatusRaw = req.getParameter("OPERATIONAL_STATUS");
            String addressCepParam = req.getParameter("ADDRESS_CEP");
            String addressNumberRaw = req.getParameter("ADDRESS_NUMBER");
            String cnpjCompanyParam = req.getParameter("CNPJ_COMPANY");

            if (cnpjParam == null || cnpjParam.isEmpty()) throw new IllegalArgumentException("Null value: 'CNPJ'");
            if (cnaeParam == null || cnaeParam.isEmpty()) throw new IllegalArgumentException("Null value: 'CNAE'");
            if (responsibleCpfParam == null || responsibleCpfParam.isEmpty()) throw new IllegalArgumentException("Null value: 'RESPONSIBLE_CPF'");
            if (operationalStatusRaw == null || operationalStatusRaw.isEmpty()) throw new IllegalArgumentException("Null value: 'OPERATIONAL_STATUS'");
            if (addressCepParam == null || addressCepParam.isEmpty()) throw new IllegalArgumentException("Null value: 'ADDRESS_CEP'");
            if (addressNumberRaw == null || addressNumberRaw.isEmpty()) throw new IllegalArgumentException("Null value: 'ADDRESS_NUMBER'");
            if (cnpjCompanyParam == null || cnpjCompanyParam.isEmpty()) throw new IllegalArgumentException("Null value: 'CNPJ_COMPANY'");

            boolean operationalStatus = Boolean.parseBoolean(operationalStatusRaw);
            int addressNumber = Integer.parseInt(addressNumberRaw);

            // [DATA ACCESS] Get plant and update
            Plant plantFromDb = PlantDAO.selectByPlantCnpj(cnpjParam);
            plantFromDb.setOperationalStatus(operationalStatus);
            plantFromDb.setAddressCep(addressCepParam);
            plantFromDb.setAddressNumber(addressNumber);
            plantFromDb.setCnae(cnaeParam);

            // [DATA ACCESS] Update Plant in database
            boolean updated = PlantDAO.update(plantFromDb);
            if (updated) {
                System.err.println("[INFO] [" + cnpjParam + "] Plant updated successfully.");
                req.setAttribute("companyCnpj", cnpjCompanyParam);
                resp.sendRedirect(req.getContextPath() + "/plant/read");
            } else {
                throw new IllegalStateException("Failure when updating plant. CNPJ:  " + cnpjParam);
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            // [FAILURE LOG] Handle validation or update failures
            System.err.println("[ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            req.setAttribute("errorMessage", "Erro ao atualizar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Catch-all for unexpected errors
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado ao atualizar a planta: " + e.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plant/read");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
