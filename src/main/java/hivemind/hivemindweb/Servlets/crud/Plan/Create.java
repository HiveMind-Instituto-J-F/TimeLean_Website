package hivemind.hivemindweb.Servlets.crud.Plan;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/create")
public class Create extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate and parse request parameters
            String durationParam = req.getParameter("duration");
            String descriptionParam = req.getParameter("description");
            String priceParam = req.getParameter("price");
            String nameParam = req.getParameter("name");

            if (durationParam == null || durationParam.isEmpty()) throw new IllegalArgumentException("Null value: 'duration'");
            if (descriptionParam == null || descriptionParam.isEmpty()) throw new IllegalArgumentException("Null value: 'description'");
            if (priceParam == null || priceParam.isEmpty()) throw new IllegalArgumentException("Null value: 'price'");
            if (nameParam == null || nameParam.isEmpty()) throw new IllegalArgumentException("Null value: 'name'");

            int duration = Integer.parseInt(durationParam);
            double price = Double.parseDouble(priceParam);

            // [PROCESS] Create local Plan object and insert into database
            Plan planLocal = new Plan(nameParam, duration, price, true);
            planLocal.setDescription(descriptionParam);

            try {
                if (PlanDAO.insert(planLocal, false)) {
                    // [SUCCESS LOG] Log successful plan creation
                    System.out.println("[INFO] [" + LocalDateTime.now() + "] Plan successfully created: " + nameParam);
                    req.setAttribute("msg", "Plano adicionado com sucesso!");
                    resp.sendRedirect(req.getContextPath() + "/plan/read");
                } else {
                    // [FAILURE LOG] Log failure when inserting plan
                    System.err.println("[ERROR] [" + LocalDateTime.now() + "] Failed to insert plan into database.");
                    req.setAttribute("errorMessage", "O plano não foi adicionado devido a um erro interno.");
                    req.getRequestDispatcher("/pages/create/plan.jsp").forward(req, resp);
                }
            } catch (NullPointerException npe) {
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException while inserting plan: " + npe.getMessage());
                req.setAttribute("errorMessage", "Erro interno ao acessar os dados do plano.");
                req.setAttribute("errorUrl", req.getContextPath() + "/plan/create");
                req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Handle invalid argument exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IllegalArgumentException occurred: " + ia.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos. Por favor, preencha todos os campos corretamente. Erro: " + ia.getMessage());
            req.getRequestDispatcher("/pages/create/plan.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] NullPointerException occurred: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao processar os dados. Verifique os campos e tente novamente.");
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/create");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Handle servlet exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] ServletException occurred: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao redirecionar a página: " + se.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/create");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] Handle IO exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] IOException occurred: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de entrada/saída ao processar a solicitação: " + ioe.getMessage());
            req.setAttribute("errorUrl", req.getContextPath() + "/plan/create");
            req.getRequestDispatcher("/pages/error/error.jsp").forward(req, resp);
        }
    }
}
