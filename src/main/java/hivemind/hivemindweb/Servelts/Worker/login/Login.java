package hivemind.hivemindweb.Servelts.Worker.login;

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
import jakarta.servlet.http.HttpSession;
import java.util.logging.Logger;

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
        if (plant == null){
            System.out.println("[WORKER-LOGIN] ERROR: plant is null");
            request.setAttribute("status", false);
            request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
            return;
        }

        Worker worker = WorkerDAO.selectByCpf(responsibleCpf);
        if (worker == null){
            System.out.println("[WORKER-LOGIN] ERROR: worker is null");
            request.setAttribute("status", false);
            request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
            return;
        }

        // Login logic
        try{
            if (plant.getResponsibleCpf().equals(worker.getCpf())){
                if(AuthService.login(responsibleLoginEmail, worker.getLoginEmail(), responsibleLoginPassword, worker.getLoginPassword())){
                    // Create session and define attributes
                    HttpSession session = request.getSession(true);

                    session.setMaxInactiveInterval(600);
                    session.setAttribute("plantCnpj", plantCnpj);
                    session.setAttribute("responsibleCpf", responsibleCpf);

                    response.sendRedirect(request.getContextPath() + "/read");
                    return;
                }
                // If did not log, dispatch to login.jsp
                System.out.println("[WORKER-LOGIN] ERROR: Incorrect Credentials");
                request.setAttribute("status", false);
                request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
            }
        } catch (NullPointerException npe){
            // Treats NullPointerException
            System.out.println("[WORKER-LOGIN] EXCEPTION: NullPointerException");
            request.setAttribute("status", false);
            request.getRequestDispatcher("html/crud/worker/login/login.jsp").forward(request, response);
        }
    }
}