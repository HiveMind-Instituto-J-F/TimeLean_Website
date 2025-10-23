package hivemind.hivemindweb.Services.Email;

import java.util.Properties;

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
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
    }

    public boolean SendEmail(String Sender , String Subject, String msg){
        try {
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_NAME, EMAIL_PASSWORD);
                }
            };
            Session session = Session.getInstance(props,auth);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Sender));
            message.setReplyTo(new Address[]{ new InternetAddress(EMAIL_NAME) });
            message.setSubject(Subject);
            message.setText(msg);
            message.setSentDate(new java.util.Date());
            
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] Falied to send email: " + e.getMessage());
            return false;
        }
    }
    
}
