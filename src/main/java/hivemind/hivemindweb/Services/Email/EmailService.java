package hivemind.hivemindweb.Services.Email;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContext;

public class EmailService {
    private static Properties props = new Properties();
    private static String EMAIL_NAME;
    private static String EMAIL_PASSWORD;


    public void init(ServletContext sce){
        Dotenv dotenv = (Dotenv) sce.getAttribute("data");
        
        if(dotenv == null){
            System.out.println("[ERROR] Dotenv Is null in Servelet Context");
            return;
        }
        
        EMAIL_NAME = dotenv.get("email_name");
        EMAIL_PASSWORD = dotenv.get("email_password");

        //SMTP = Simple Mail Transfer Protocol
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
    }

    public PasswordAuthentication AccountAuthentication(){
        return new PasswordAuthentication(EMAIL_NAME,EMAIL_PASSWORD);
    }

    public boolean SendEmail(String Sender, String Recipient , String Subject, String msg, ServletContext sce){
        try {
            PasswordAuthentication Password = this.AccountAuthentication(); 
            Session session = Session.getInstance(props,Password);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender)); // remetente visível
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(msg); // corpo do e-mail (texto simples)
            message.setSentDate(new java.util.Date());
            
        } catch (Exception e) {
        }

    }
    
}
