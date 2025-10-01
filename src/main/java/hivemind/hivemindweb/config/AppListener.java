package hivemind.hivemindweb.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            System.out.println("=================================================");
            System.out.println("[WARN] Application startup - Initializing services");
            System.out.println("=================================================");
            
            // Informações de debug
            System.out.println("[DEBUG] catalina.base: " + System.getProperty("catalina.base"));
            System.out.println("[DEBUG] user.dir: " + System.getProperty("user.dir"));
            System.out.println("[DEBUG] Context path: " + sce.getServletContext().getContextPath());
            
            // Inicializa o EnvLoader
            EnvLoader.init(sce.getServletContext());        
            System.out.println("=================================================");
        }
        catch (Exception de){
            System.out.println("[ERROR] In AppListener");
        }
    }

    // Called automatically by the servlet container
    // when the application is shutting down. 
    // Use this method to release resources (DB connections, threads, etc).
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[WARN] Application shutdown");
    }
}