package hivemind.hivemindweb.Servelts.Email;

import java.io.IOException;

import hivemind.hivemindweb.Services.Email.EmailService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/email/send")
public class Send extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String sector = req.getParameter("sector");
            if(sector == null || sector.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'sector'");}

            String sender = req.getParameter("sender");
            if(sender == null || sender.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'sender'");}

            String subject = req.getParameter("subject");
            if(subject == null || subject.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'subject'");}

            String userMessage = req.getParameter("msg");
            if(userMessage == null || userMessage.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'msg'");
            }

            String msg = "Olá,\n\n"
                + "Você recebeu um novo contato de um cliente da HiveMind referente à aplicação TimeLean.\n"
                + "Setor escolhido pelo usuário: " + sector + "\n"
                + "Cliente/Remetente: " + sender + "\n\n"
                + "Mensagem do cliente:\n" + userMessage + "\n\n"
                + "Observação: Este e-mail foi enviado pelo sistema da HiveMind e será direcionado à equipe responsável.\n\n"
                + "Atenciosamente,\n"
                + "Equipe HiveMind";
            
            ServletContext context = req.getServletContext();
            EmailService Email = (EmailService) context.getAttribute("EmailService");
            if (Email.SendEmail("", subject, msg)) {
                System.out.println("[INFO] Email sent successfully");
                req.setAttribute("msg", "Email sent successfully!");
                req.getRequestDispatcher("/html/email/send.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("msg", "Email not sent successfully!");
            req.getRequestDispatcher("/html/email/send.jsp").forward(req, resp);
        }catch(IllegalArgumentException se){
            System.err.println("[ERROR] Error In Create Servelet, Error: "+ se.getMessage());
            req.setAttribute("error", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("/html/email/send.jsp").forward(req, resp);
        }
    }
    
}
