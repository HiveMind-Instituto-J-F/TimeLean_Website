package hivemind.hivemindweb.Servelts.Plant;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update-plant")
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cnpj = request.getParameter("cnpj");

        Plant plant = PlantDAO.selectByPlantCnpj(cnpj);
        request.setAttribute("plant", plant);

        request.getRequestDispatcher("html/crud/plant/modify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cnpj = request.getParameter("CNPJ");
        String cnae = request.getParameter("CNAE");
        String responsibleCpf = request.getParameter("RESPONSIBLE_CPF");
        boolean operationalStatus = Boolean.parseBoolean(request.getParameter("OPERATIONAL_STATUS"));
        String addressCep = request.getParameter("ADDRESS_CEP");
        int addressNumber = Integer.parseInt(request.getParameter("ADDRESS_NUMBER"));
        String cnpjCompany = request.getParameter("CNPJ_COMPANY");

        Plant plant = new Plant(cnpj, cnae, responsibleCpf, operationalStatus, addressCep, addressNumber, cnpjCompany);

        if (PlantDAO.update(plant)) {
            // Forward to the list page with the updated company CNPJ
            request.setAttribute("companyCnpj", cnpjCompany);
            response.sendRedirect("html/crud/plant/select.jsp");
        } else {
            throw new ServletException("Failed to update plant with CNPJ: " + cnpj);
        }
    }

}
