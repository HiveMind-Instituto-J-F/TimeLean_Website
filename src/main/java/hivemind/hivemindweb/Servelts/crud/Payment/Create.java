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
            String beneficiaryParam = req.getParameter("beneficiary");
            String deadlineParam = req.getParameter("deadline");
            String idPlanSubParam = req.getParameter("id_plan_sub");

            if (methodParam != null) methodParam = methodParam.trim();
            if (beneficiaryParam == null || beneficiaryParam.isEmpty()) throw new IllegalArgumentException("Null value: 'beneficiary'");
            if (idPlanSubParam == null || idPlanSubParam.isEmpty()) throw new IllegalArgumentException("Null value:'id_plan_sub'");
            if (deadlineParam == null || deadlineParam.isEmpty()) throw new IllegalArgumentException("Null value: 'deadline'");

            LocalDate deadline = LocalDate.parse(deadlineParam);
            String status = "Pendente";
            int idPlanSub = Integer.parseInt(idPlanSubParam);

            // [DATA ACCESS] Calculate payment value
            double value = PlanDAO.getPrice(idPlanSub) / PlanSubscriptionDAO.select(idPlanSub).getNumberInstallments();

            // [LOGIC] Create payment object
            Payment paymentLocal = new Payment(value, deadline, methodParam, beneficiaryParam, status, idPlanSub);

            // [DATA ACCESS] Insert payment into DB
            if (PaymentDAO.insert(paymentLocal)) {
                // [SUCCESS LOG] Payment added successfully
                System.err.println("[INFO] Payment added successfully, id_plan_sub: " + idPlanSub);
            } else {
                // [FAILURE LOG] Failed DB insertion
                System.err.println("[ERROR] Failed to add payment to DB, id_plan_sub: " + idPlanSub);
                req.setAttribute("errorMessage", "Não foi possível adicionar o pagamento. Verifique os dados e tente novamente. (OBS: data dever ser maior que o dia atual)");
                req.getRequestDispatcher("/html/crud/payment/create.jsp").forward(req, resp);
                return;
            }

            // Redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/payment/read");

        } catch (IllegalArgumentException ia) {
            // [FAILURE LOG] Invalid input
            System.err.println("[ERROR] IllegalArgumentException: " + ia.getMessage());
            req.setAttribute("errorMessage", "Dados inválidos, Por favor, preencha todos os campos corretamente. Erro: " + ia.getMessage());
            req.getRequestDispatcher("/html/crud/payment/create.jsp").forward(req, resp);

        } catch (DateTimeParseException dpe) {
            // [FAILURE LOG] Date parsing error
            System.err.println("[ERROR] DateTimeParseException: " + dpe.getMessage());
            req.setAttribute("errorMessage", "Data inválida: " + dpe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [FAILURE LOG] Servlet dispatch error
            System.err.println("[ERROR] ServletException: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro ao processar a requisição no servidor: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe){
            // [FAILURE LOG] NullPointerException exception
            System.err.println("[ERROR] Unexpected error: " + npe.getMessage());
            req.setAttribute("errorMessage", "Algum valor inserido é nulo");
            req.setAttribute("errorUrl", "/html/crud/payment/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
