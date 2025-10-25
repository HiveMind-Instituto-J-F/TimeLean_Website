package hivemind.hivemindweb.Servelets.crud.Worker.login;

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
import java.time.LocalDateTime;

@WebServlet("/worker/login")
public class Login extends HttpServlet {

    @Override
<<<<<<< HEAD:src/main/java/hivemind/hivemindweb/Servelets/crud/Worker/login/Login.java
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
=======
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // [VALIDATION] Get parameters
        String paramPlantCnpj = req.getParameter("plant-cnpj");
        String paramResponsibleCpf = req.getParameter("plant-responsible-cpf");
        String paramResponsibleLoginEmail = req.getParameter("plant-responsible-login-email");
        String paramResponsibleLoginPassword = req.getParameter("plant-responsible-login-password");

        try {
            // [DATA ACCESS] Validate plant
            Plant plant = PlantDAO.selectByPlantCnpj(paramPlantCnpj);
            if (plant == null) {
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Plant not found for CNPJ: " + paramPlantCnpj);
                req.setAttribute("errorMessage", "Planta não encontrada.");
                req.setAttribute("errorUrl", "/html/crud/worker/login/login.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [DATA ACCESS] Validate worker
            Worker worker = WorkerDAO.selectByCpf(paramResponsibleCpf);
            if (worker == null) {
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Worker not found for CPF: " + paramResponsibleCpf);
                req.setAttribute("errorMessage", "Trabalhador não encontrado.");
                req.setAttribute("errorUrl", "/html/crud/worker/login/login.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Validate responsible relation and login
            if (!plant.getResponsibleCpf().equals(worker.getCpf())) {
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Plant responsible mismatch with worker CPF.");
                req.setAttribute("status", false);
                req.getRequestDispatcher("/html/crud/worker/login/login.jsp").forward(req, resp);
                return;
            }

            boolean loginOk = AuthService.login(
                    paramResponsibleLoginEmail,
                    worker.getLoginEmail(),
                    paramResponsibleLoginPassword,
                    worker.getLoginPassword()
            );

            if (loginOk) {
                // [SUCCESS LOG] Create session and redirect
                HttpSession session = req.getSession(true);
                session.setMaxInactiveInterval(600);
                session.setAttribute("plantCnpj", paramPlantCnpj);
                session.setAttribute("responsibleCpf", paramResponsibleCpf);

                System.err.println("[SUCCESS LOG] [" + LocalDateTime.now() + "] Worker login successful for CPF: " + paramResponsibleCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Invalid credentials
                System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Invalid credentials for worker CPF: " + paramResponsibleCpf);
                req.setAttribute("status", false);
                req.getRequestDispatcher("/html/crud/worker/login/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            // [FAILURE LOG] Catch all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error in Worker.Login: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado durante o login: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/worker/login/login.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
>>>>>>> 350d8ab7eb3a2ea5bf518c8c121e454150a4ec26:src/main/java/hivemind/hivemindweb/Servelts/crud/Worker/login/Login.java
        }
    }
}
