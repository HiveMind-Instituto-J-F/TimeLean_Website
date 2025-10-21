package hivemind.hivemindweb.Servelts.Payment.update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/udpate-paymount")
public class Update extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            if (idStr.isEmpty()) {throw new IllegalArgumentException("Valueis Nulo, Value: 'id'");}
            int id = Integer.parseInt(idStr);
            
            String method = req.getParameter("method");
            if (method != null) method = method.trim();

            String beneficary = req.getParameter("beneficiary");
            if (beneficary.isEmpty()) {throw new IllegalArgumentException("Valueis Nulo, Value: 'beneficary'");}
            
            String deadlineStr = req.getParameter("deadline");
            if (deadlineStr.isEmpty()) {throw new IllegalArgumentException("Valueis Nulo, Value: 'deadline'");}
            LocalDate deadline = LocalDate.parse(deadlineStr);

            String status = req.getParameter("status");
            if(status.isEmpty()){throw new IllegalArgumentException("Valueis Nulo, Value: 'status'");}

            String number_installmentsStr = req.getParameter("number_installments");
            System.out.println(number_installmentsStr);
            if(number_installmentsStr == null || number_installmentsStr.isEmpty()){throw new IllegalArgumentException("Valueis Nulo, Value: 'number_installments'");}
            int number_installments = Integer.parseInt(number_installmentsStr);
            if(number_installments <= 0){throw new IllegalArgumentException("number_installments is bellow of 0");}

            String idPlanStr = req.getParameter("id_plan_sub");
            if(idPlanStr.isEmpty()){throw new IllegalArgumentException("Valueis Nulo, Value: 'idPlanSub'");}
            int idPlanSub = Integer.parseInt(idPlanStr);
        
            if ((method == null || method.isEmpty()) || (beneficary == null || beneficary.isEmpty()) || deadline == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valores Sao inválidos ou nulos.");
                throw new ServletException("Values Is Null");
            }

            Payment paymentLocal = new Payment(id, deadline, method, beneficary, status, idPlanSub);

            if(PaymentDAO.update(paymentLocal)){
                System.out.println("[WARN] Update Payment Sussefly");
                req.setAttribute("msg", "Pagamento foi Atalizado com Susseso!");
            }
            else{
                System.out.println("[WARN] Erro in PaymentDAO");
                req.setAttribute("msg", "Pagamento Nao foi Atalizado devido a um Erro!");
            }
            req.getRequestDispatcher("html\\crud\\paymount.jsp").forward(req, resp);
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Payment Add, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", se);
        }
        catch(IllegalArgumentException ime){
            System.out.println("[ERROR] Invaliad Input, Erro: " + ime.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + ime.getMessage());
        }
        catch(DateTimeParseException dpe){
            System.out.println("[ERRO] Failead Convert Date, Erro: " + dpe.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + dpe.getMessage());
            req.setAttribute("error", dpe.getCause());
        }
    }
}
