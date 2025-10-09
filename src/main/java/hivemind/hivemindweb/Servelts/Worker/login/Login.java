package hivemind.hivemindweb.Servelts.Worker;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Plant;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/plant-login")
public class Login extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters:
        String plantCnpj = request.getParameter("plant-cnpj");
        String responsibleCpf = request.getParameter("plant-responsible-cpf");
        String responsibleLoginEmail = request.getParameter("plant-responsible-login-email");
        String responsibleLoginPassword = request.getParameter("plant-responsible-login-password");

        // Create objects:
        Plant plant = PlantDAO.selectByPlantCnpj(plantCnpj);
        Worker worker = WorkerDAO.selectByCpf(responsibleCpf);

        try{
            if (plant.getResponsibleCpf().equals(worker.getCpf())){
                if(AuthService.login(responsibleLoginEmail, worker.getLoginEmail(),
                        worker.getLoginPassword(), responsibleLoginPassword)){
                    request.setAttribute("plantCnpj", plantCnpj);
                    request.getRequestDispatcher("html/crud/worker/read.jsp").forward(request, response);
                } else {
                    request.setAttribute("status", false);
                    request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
                }
            }
        } catch (NullPointerException npe){
            request.setAttribute("status", false);
            request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
        }
    }
}