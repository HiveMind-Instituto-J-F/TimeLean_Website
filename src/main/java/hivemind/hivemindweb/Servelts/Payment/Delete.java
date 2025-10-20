package hivemind.hivemindweb.Servelts.Payment;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import hivemind.hivemindweb.DAO.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete-paymount")
public class Delete extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            if (idStr.isEmpty()) {throw new IllegalArgumentException("Valueis Nulo, Value: 'id'");}
            int id = Integer.parseInt(idStr);
        
            if(PaymentDAO.delete(id)){
                System.out.println("[WARN] Delete Payment Sussefly");
                req.setAttribute("msg", "Pagamento foi Deletado com Susseso!");
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
