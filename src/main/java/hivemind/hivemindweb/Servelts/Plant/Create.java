package hivemind.hivemindweb.Servelts.Plant;


import java.io.IOException;

import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.models.Plant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create-plant")
public class Create extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("company_cnpj", request.getParameter("company-cnpj"));
        request.getRequestDispatcher("html/crud/plant/create.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer message;

        // Get Attributes:
        String cnpj = request.getParameter("cnpj");
        String cnae = request.getParameter("cnae");
        String operationalStatusString = request.getParameter("operational_status");
        String responsibleCpf = request.getParameter("responsible_cpf");
        int addressNumber = Integer.parseInt(request.getParameter("address_number"));
        String addressCep = request.getParameter("address_cep");
        Boolean operationalStatus;

        if (operationalStatusString.equals("Active")){
            operationalStatus = true;
        } else{
            operationalStatus = false;
        }

        Plant plant = new Plant(cnpj, cnae, responsibleCpf,
                operationalStatus, addressCep, addressNumber,
                (String) request.getAttribute("company_cnpj"));
        System.out.println(plant);
        if (PlantDAO.insert(plant)){
            response.sendRedirect("html/crud/plant/read.jsp");
        } else{
            throw new ServletException();
        }




    }
}
