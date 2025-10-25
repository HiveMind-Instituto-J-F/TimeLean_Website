package hivemind.hivemindweb.Servelts.crud.Plan;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.Exception.InvalidForeignKeyException;
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
            if (durationParam == null || durationParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'duration'");
            }
            int duration = Integer.parseInt(durationParam);

            String descriptionParam = req.getParameter("description");
            if (descriptionParam == null || descriptionParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'description'");
            }

            String priceParam = req.getParameter("price");
            if (priceParam == null || priceParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'price'");
            }
            double price = Double.parseDouble(priceParam);

            String nameParam = req.getParameter("name");
            if (nameParam == null || nameParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            }

            // [LOGIC] Create local Plan object and insert into database
            Plan planLocal = new Plan(nameParam, descriptionParam, duration, price);
            planLocal.setActive(true);

            if (PlanDAO.insert(planLocal, false)) {
                // [SUCCESS LOG] Log successful plan creation
                System.err.println("[SUCCESS LOG] Plan successfully created: " + nameParam);
                req.setAttribute("msg", "Plano adicionado com sucesso!");
                resp.sendRedirect(req.getContextPath() + "/plan/read");
            } else {
                // [FAILURE LOG] Log failure when inserting plan
                System.err.println("[FAILURE LOG] Failed to insert plan into database.");
                req.setAttribute("errorMessage", "O plano não foi adicionado devido a um erro interno.");
                req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Handle invalid argument exception
            System.err.println("[FAILURE LOG] IllegalArgumentException occurred: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro nos dados fornecidos: " + ia.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[FAILURE LOG] NullPointerException occurred: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao processar os dados. Verifique os campos e tente novamente.");
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Handle servlet exception
            System.err.println("[FAILURE LOG] ServletException occurred: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao redirecionar a página: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [FAILURE LOG] Handle IO exception
            System.err.println("[FAILURE LOG] IOException occurred: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de entrada/saída ao processar a solicitação: " + ioe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Handle unexpected exceptions
            System.err.println("[FAILURE LOG] Unexpected exception occurred: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
