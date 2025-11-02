package hivemind.hivemindweb.Services.AppListener;

import java.time.LocalDateTime;

import hivemind.hivemindweb.Services.Email.EmailService;
import hivemind.hivemindweb.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            // [PROCESS] Log application startup
            System.out.println("=================================================");
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Application startup - Initializing services");
            System.out.println("=================================================");

            // [PROCESS] Log environment debug info
            System.out.println("[INFO] [" + LocalDateTime.now() + "] catalina.base: " + System.getProperty("catalina.base"));
            System.out.println("[INFO] [" + LocalDateTime.now() + "] user.dir: " + System.getProperty("user.dir"));
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Context path: " + event.getServletContext().getContextPath());

            // [PROCESS] Initialize environment loader
            EnvLoader.init(event.getServletContext());
            Dotenv dotenvFromDb = EnvLoader.getDotenv();
            event.getServletContext().setAttribute("data", dotenvFromDb);

            // [DATA ACCESS] Initialize and store EmailService
            EmailService emailServiceFromDb = new EmailService();
            emailServiceFromDb.init(event.getServletContext());
            event.getServletContext().setAttribute("EmailService", emailServiceFromDb);

            // [SUCCESS LOG] Log successful initialization
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Services initialized successfully");
            System.out.println("=================================================");

        } catch (NullPointerException e) {
            // [FAILURE LOG] Log null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [AppListener] NullPointerException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Log illegal argument error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [AppListener] IllegalArgumentException: " + e.getMessage());
        } catch (Exception e) {
            // [FAILURE LOG] Log generic error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [AppListener] Exception: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // [PROCESS] Log application shutdown
        System.out.println("[INFO] [" + LocalDateTime.now() + "] Application shutdown");
    }
}
