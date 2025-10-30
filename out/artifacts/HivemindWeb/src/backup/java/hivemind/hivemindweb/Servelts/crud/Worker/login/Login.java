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
import java.time.LocalDateTime;

@WebServlet("/worker/login")
public class Login extends HttpServlet {

    @Override
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
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Plant responsible mismatch with worker CPF.");
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

                System.err.println("[INFO] [" + LocalDateTime.now() + "] Worker login successful for CPF: " + paramResponsibleCpf);
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Invalid credentials
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Invalid credentials for worker CPF: " + paramResponsibleCpf);
                req.setAttribute("status", false);
                req.getRequestDispatcher("/html/crud/worker/login/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            // [FAILURE LOG] Catch all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error in Worker.Login: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado durante o login: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/worker/login/login.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
