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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [VALIDATION] Get parameters
        String paramPlantCnpj = req.getParameter("plant-cnpj");
        if(paramPlantCnpj == null || paramPlantCnpj.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'paramPlantCnpj'");}

        String paramResponsibleCpf = req.getParameter("plant-responsible-cpf");
        if(paramResponsibleCpf == null || paramResponsibleCpf.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'paramResponsibleCpf'");}

        String paramResponsibleLoginEmail = req.getParameter("plant-responsible-login-email");
        if(paramResponsibleLoginEmail == null || paramResponsibleLoginEmail.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'paramResponsibleLoginEmail'");}

        String paramResponsibleLoginPassword = req.getParameter("plant-responsible-login-password");
        if(paramResponsibleLoginPassword == null || paramResponsibleLoginPassword.isEmpty()){throw new IllegalArgumentException("Values Is Null, paramResponsibleLoginPassword: 'paramResponsibleLoginEmail'");}

        try {
            // [DATA ACCESS] Validate plant
            Plant plant = PlantDAO.selectByPlantCnpj(paramPlantCnpj);
            if (plant == null) {
                // [FAILURE LOG] Plant not found
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Plant not found for CNPJ: " + paramPlantCnpj);
                req.setAttribute("errorMessage", "Planta não encontrada.");
                req.setAttribute("errorUrl", "/pages/workerLogin.jsp");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
                return;
            }

            // [DATA ACCESS] Validate worker
            Worker worker = WorkerDAO.selectByCpf(paramResponsibleCpf);
            if (worker == null) {
                // [FAILURE LOG] Worker not found
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Worker not found for CPF: " + paramResponsibleCpf);
                req.setAttribute("errorMessage", "Trabalhador não encontrado.");
                req.setAttribute("errorUrl", "/pages/workerLogin.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // [PROCESS] Validate responsible relation and login
            if (!plant.getResponsibleCpf().equals(worker.getCpf())) {
                // [FAILURE LOG] CPF mismatch
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Plant responsible mismatch with worker CPF.");
                req.setAttribute("status", false);
                req.getRequestDispatcher("/pages/workerLogin.jsp").forward(req, resp);
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

                System.out.println("[INFO] [" + LocalDateTime.now() + "] Worker registered successfully for CPF: " + paramResponsibleCpf); // [SUCCESS LOG] Login successful
                resp.sendRedirect(req.getContextPath() + "/worker/read");
            } else {
                // [FAILURE LOG] Invalid credentials
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] Invalid credentials for worker CPF: " + paramResponsibleCpf); // [FAILURE LOG] Invalid credentials
                req.setAttribute("status", false);
                req.getRequestDispatcher("/pages/workerLogin.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input parameter
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Valores inválidos ou parâmetro ausente.");
            req.getRequestDispatcher("/pages/workerLogin.jsp").forward(req, resp);
        } catch (NullPointerException npe) {
            // [FAILURE LOG] Null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException: " + npe.getMessage());
            req.setAttribute("errorMessage", "Erro interno: valor nulo inesperado.");
            req.setAttribute("errorUrl", "/pages/workerLogin.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        } catch (Exception e) {
            // [FAILURE LOG] Catch all unexpected errors
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] Unexpected error in Worker.Login: " + e.getMessage());
            req.setAttribute("errorMessage", "Erro inesperado durante o login: " + e.getMessage());
            req.setAttribute("errorUrl", "/pages/workerLogin.jsp");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
