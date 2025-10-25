package hivemind.hivemindweb.Servelts.crud.Payment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.DAO.PlanSubscriptionDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [PROCESS] Handle creation of a new payment
        try {
            String methodParam = req.getParameter("method");
            if (methodParam != null) methodParam = methodParam.trim();

            String beneficiaryParam = req.getParameter("beneficiary");
            if (beneficiaryParam == null || beneficiaryParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'beneficiary' não informado.");
            }

            String deadlineParam = req.getParameter("deadline");
            if (deadlineParam == null || deadlineParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'deadline' não informado.");
            }
            LocalDate deadline = LocalDate.parse(deadlineParam);

            String status = "PENDING";

            String idPlanSubParam = req.getParameter("id_plan_sub");
            if (idPlanSubParam == null || idPlanSubParam.isEmpty()) {
                throw new IllegalArgumentException("Parâmetro 'id_plan_sub' não informado.");
            }
            int idPlanSub = Integer.parseInt(idPlanSubParam);

            // [DATA ACCESS] Calculate payment value
            double value = PlanDAO.getPrice(idPlanSub) / PlanSubscriptionDAO.select(idPlanSub).getNumberInstallments();

            // [VALIDATION] Ensure all required fields are valid
            if (methodParam == null || methodParam.isEmpty() || beneficiaryParam.isEmpty() || deadline == null) {
                throw new ServletException("Valores inválidos ou nulos.");
            }

            // [LOGIC] Create payment object
            Payment paymentLocal = new Payment(value, deadline, methodParam, beneficiaryParam, status, idPlanSub);

            // [DATA ACCESS] Insert payment into DB
            if (PaymentDAO.insert(paymentLocal)) {
                // [SUCCESS LOG] Payment added successfully
                System.err.println("[SUCCESS] Payment added successfully, id_plan_sub: " + idPlanSub);
                req.setAttribute("msg", "Pagamento foi adicionado com sucesso!");
            } else {
                // [FAILURE LOG] Failed DB insertion
                System.err.println("[FAILURE] Failed to add payment to DB, id_plan_sub: " + idPlanSub);
                req.setAttribute("errorMessage", "Pagamento não foi adicionado devido a um erro no banco de dados.");
                req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Invalid input
            System.err.println("[FAILURE] IllegalArgumentException: " + iae.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos: " + iae.getMessage());
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Date parsing error
            System.err.println("[FAILURE] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[FAILURE] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [FAILURE LOG] Unexpected exception
            System.err.println("[FAILURE] Unexpected error: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado ao criar o pagamento.");
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
