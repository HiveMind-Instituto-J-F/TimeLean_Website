package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;

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
            if (deadlineStr == null && deadlineStr.isEmpty()) {throw new InputMismatchException("Valueis Nulo, Value: 'deadline'");}
            LocalDate deadline = LocalDate.parse(deadlineStr);

            String status = req.getParameter("status");
            if(status == null && status.isEmpty()){throw new InputMismatchException("Valueis Nulo, Value: 'status'");}

            String installmentCountStr = req.getParameter("installmentCount");
            if(installmentCountStr == null && installmentCountStr.isEmpty()){throw new InputMismatchException("Valueis Nulo, Value: 'installmentCount'");}
            int installmentCount = Integer.parseInt(installmentCountStr);

            String idStr = req.getParameter("id_plan_sub");
            if(idStr == null && idStr.isEmpty()){throw new InputMismatchException("Valueis Nulo, Value: 'idPlanSub'");}
            int id_plan_sub = Integer.parseInt(idStr);
            
            double value = PaymentDAO.getPrice(id_plan_sub) / installmentCount; 

            if ((method == null || method.isEmpty()) || (beneficary == null || beneficary.isEmpty()) || deadline == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valores Sao inválidos ou nulos.");
                throw new ServletException("Values Is Null");
            }

            System.out.println("[DEBUG] Values: value=" + value +
                ", deadline=" + deadline +
                ", method=" + method +
                ", beneficiary=" + beneficary +
                ", id_plan_sub=" + id_plan_sub);


            Payment paymentLocal = new Payment(value, deadline, method, beneficary, status, id_plan_sub);
            System.out.println(paymentLocal);
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
        catch(InputMismatchException ime){
            System.out.println("[ERROR] Invaliad Input, Erro: " + ime.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos: " + ime.getMessage());
        }catch (NumberFormatException nfe) {
            System.out.println("[ERROR] Invalid Number Format: " + nfe.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de número inválido");
        }
    }
}
