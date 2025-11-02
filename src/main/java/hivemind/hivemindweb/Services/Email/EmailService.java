package hivemind.hivemindweb.Services.Email;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContext;

public class EmailService {

    private static final Properties smtpProperties = new Properties();
    private static String emailName;
    private static String emailPassword;

    public void init(ServletContext context) {
        try {
            // [DATA ACCESS] Retrieve environment variables from servlet context
            Dotenv dotenvFromDb = (Dotenv) context.getAttribute("data");

            // [VALIDATION] Validate dotenv presence
            if (dotenvFromDb == null) {
                throw new IllegalArgumentException("Valor Nulo: 'data'");
            }

            // [PROCESS] Load email credentials
            emailName = dotenvFromDb.get("EMAIL_NAME");
            emailPassword = dotenvFromDb.get("EMAIL_PASSWORD");

            // [PROCESS] Configure SMTP properties
            smtpProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            smtpProperties.put("mail.smtp.starttls.enable", "true");
            smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
            smtpProperties.put("mail.smtp.port", "587");
            smtpProperties.put("mail.smtp.auth", "true");
            smtpProperties.put("mail.smtp.connectiontimeout", "8000");
            smtpProperties.put("mail.smtp.timeout", "8000");
            smtpProperties.put("mail.smtp.writetimeout", "8000");

            // [SUCCESS LOG] Log successful initialization
            System.out.println("[INFO] [" + LocalDateTime.now() + "] EmailService initialized successfully");

        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Log missing dotenv error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] IllegalArgumentException: " + e.getMessage());
        } catch (NullPointerException e) {
            // [FAILURE LOG] Log null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] NullPointerException: " + e.getMessage());
        } catch (Exception e) {
            // [FAILURE LOG] Log generic initialization error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] Exception: " + e.getMessage());
        }
    }

    public boolean sendEmail(String subject, String messageContent) {
        try {
            // [PROCESS] Create authenticator with email credentials
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailName, emailPassword);
                }
            };

            // [PROCESS] Create mail session
            Session session = Session.getInstance(smtpProperties, authenticator);

            // [PROCESS] Construct email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailName));
            message.setReplyTo(new Address[]{new InternetAddress(emailName)});
            message.setSubject(subject);
            message.setText(messageContent);
            message.setSentDate(new Date());

            // [BUSINESS RULES] Send email
            Transport.send(message);

            // [SUCCESS LOG] Log successful email sending
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Email sent successfully");
            return true;

        } catch (MessagingException e) {
            // [FAILURE LOG] Log messaging error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] MessagingException: " + e.getMessage());
        } catch (NullPointerException e) {
            // [FAILURE LOG] Log null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] NullPointerException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Log illegal argument error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] IllegalArgumentException: " + e.getMessage());
        } catch (Exception e) {
            // [FAILURE LOG] Log generic error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EmailService] Exception: " + e.getMessage());
        }

        return false;
    }
}
