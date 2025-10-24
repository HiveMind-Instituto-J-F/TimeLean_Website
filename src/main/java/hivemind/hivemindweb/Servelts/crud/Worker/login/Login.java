package hivemind.hivemindweb.Servelts.crud.Worker.login;

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
import java.io.IOException;

@WebServlet("/worker/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Get parameters:
            String plantCnpj = request.getParameter("plant-cnpj");
            if(plantCnpj == null || plantCnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'plantCnpj'");}
            
            String responsibleCpf = request.getParameter("plant-responsible-cpf");
            if(responsibleCpf == null || responsibleCpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'responsibleCpf'");}

            String responsibleLoginEmail = request.getParameter("plant-responsible-login-email");
            if(responsibleLoginEmail == null || responsibleLoginEmail.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'responsibleLoginEmail'");}

            String responsibleLoginPassword = request.getParameter("plant-responsible-login-password");
            if(responsibleLoginEmail == null || responsibleLoginEmail.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'responsibleLoginEmail'");}

            // Validate plant
            Plant plant = PlantDAO.selectByPlantCnpj(plantCnpj);
            if (plant == null) {
                throw new NullPointerException("plant is null");
            }

            // Validate worker
            Worker worker = WorkerDAO.selectByCpf(responsibleCpf);
            if (worker == null) {
                throw new NullPointerException("plant is null");
            }

            // Login logic
            if (plant.getResponsibleCpf().equals(worker.getCpf())) {
                boolean loginOk = AuthService.login(
                        responsibleLoginEmail,
                        worker.getLoginEmail(),
                        responsibleLoginPassword,
                        worker.getLoginPassword()
                );

                if (loginOk) {
                    // Create session and define attributes
                    HttpSession session = request.getSession(true);
                    session.setMaxInactiveInterval(600);
                    session.setAttribute("plantCnpj", plantCnpj);
                    session.setAttribute("responsibleCpf", responsibleCpf);

                    response.sendRedirect(request.getContextPath() + "/worker/read");
                    return;
                }

                // Wrong credentials
                System.out.println("[ERROR] Incorrect Credentials");
                request.setAttribute("status", false);
                request.getRequestDispatcher("/html/crud/worker/login/login.jsp").forward(request, response);
            }
        } catch (NullPointerException npe) {
            System.out.println("[ERROR] " + npe.getMessage());
            request.setAttribute("status", false);
            request.getRequestDispatcher("/html/crud/worker/login/login.jsp").forward(request, response);
        }
    }
}