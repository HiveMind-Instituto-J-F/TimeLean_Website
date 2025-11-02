package hivemind.hivemindweb.Connection;

import hivemind.hivemindweb.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBConnection {

    public Connection connected() {
        Connection connection = null;

        try {
            // [PROCESS] Load environment variables
            Dotenv dotenv = EnvLoader.getDotenv();
            Class.forName("org.postgresql.Driver");

            // [VALIDATION] Validate required environment variables
            String dbUrl = dotenv.get("DB_HOST_NAME");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            if (dbUrl == null) throw new IllegalArgumentException("Valor Nulo: 'DB_HOST_NAME'");
            if (dbUser == null) throw new IllegalArgumentException("Valor Nulo: 'DB_USER'");
            if (dbPassword == null) throw new IllegalArgumentException("Valor Nulo: 'DB_PASSWORD'");

            // [DATA ACCESS] Establish database connection
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // [SUCCESS LOG] Log successful connection
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Database connection established successfully.");

        } catch (IllegalArgumentException iae) {
            // [FAILURE LOG] Log missing environment variable
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [DBConnection] IllegalArgumentException: " + iae.getMessage());
        } catch (NullPointerException npe) {
            // [FAILURE LOG] Log null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [DBConnection] NullPointerException: " + npe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            // [FAILURE LOG] Log driver class not found
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [DBConnection] ClassNotFoundException: " + cnfe.getMessage());
        } catch (SQLException sqle) {
            // [FAILURE LOG] Log SQL exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [DBConnection] SQLException: " + sqle.getMessage());
        } catch (Exception e) {
            // [FAILURE LOG] Log generic exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [DBConnection] Exception: " + e.getMessage());
        }

        return connection;
    }
}
