package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import java.time.LocalDate;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/paymount")
public class PaymountServelet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String method = req.getParameter("method");
            if (method != null) method = method.trim();

            String beneficary = req.getParameter("beneficiary");

            String deadlineStr = req.getParameter("deadline");
            LocalDate deadline = null;
            if (deadlineStr != null && !deadlineStr.isEmpty()) {
                deadline = LocalDate.parse(deadlineStr);
            }

            boolean status = Boolean.parseBoolean(req.getParameter("status"));

            String idStr = req.getParameter("id_plan_sub");
            int id_plan_sub = 0;
            if (idStr != null && !idStr.isEmpty()) {
                id_plan_sub = Integer.parseInt(idStr);
            }

            double value = PaymentDAO.getPrice(id_plan_sub);

            if ((method == null || method.isEmpty()) || (beneficary == null || beneficary.isEmpty()) || deadline == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valores Sao inválidos ou nulos.");
                throw new ServletException("Valores são inválidos ou nulos.");
            }

            Payment paymentLocal = new Payment(value, deadline, method, beneficary, beneficary, id_plan_sub);
            if(PaymentDAO.insert(paymentLocal)){
                System.out.println("[WARN] Insert Payment Sussefly");
            }
            else{
                System.out.println("[WARN] Erro In Insert");
                System.out.println("Payment class: " + paymentLocal);
            }
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Payment Add, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", se);
        }
    }
}
