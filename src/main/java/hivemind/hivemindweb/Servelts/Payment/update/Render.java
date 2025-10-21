package hivemind.hivemindweb.Servelts.Payment.update;

import java.io.IOException;
import java.util.List;

import hivemind.hivemindweb.DAO.PaymentDAO;
import hivemind.hivemindweb.models.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment/render")
public class Render extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // Create and validate company
            List<Payment> paymentList;
            paymentList = PaymentDAO.select();
            System.out.println("[INF] [" + java.time.LocalDateTime.now() + "] Payment.Render -> Payment list successfully loaded. Total: " + paymentList.size());

            if (paymentList == null || paymentList.isEmpty()){
                throw new NullPointerException("Values Is Null, Value: 'paymentList'");
            }
            
            // Render and dispatch company
            req.setAttribute("payments", paymentList);
            req.getRequestDispatcher("/html/crud/payment/read.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }catch (NullPointerException npe) {
            // Redirect to error.jsp in case of NullPointerException
            System.err.println("[WARN] ERROR: NullPointerException");
            req.getRequestDispatcher("\\html\\crud\\company\\error\\error.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
    }
}
    
