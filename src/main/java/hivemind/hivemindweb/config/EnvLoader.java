package hivemind.hivemindweb.config;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.servlet.ServletContext;

public class EnvLoader {

    private static Dotenv dotenv = null;
    private static final String envFileName = ".env";

    // [PROCESS] Initializes Dotenv configuration from /WEB-INF directory
    public static void init(ServletContext servletContext) {
        try {
            // [DATA ACCESS] Retrieve directory path from servlet context
            Optional<Path> directoryPath = getDirectoryPath(servletContext);

            if (directoryPath.isPresent()) {
                dotenv = Dotenv.configure()
                        .ignoreIfMissing()
                        .directory(directoryPath.get().toString())
                        .filename(envFileName)
                        .load();

                // [SUCCESS LOG] Dotenv loaded successfully
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Dotenv file loaded from /WEB-INF");
            } else {
                // [FAILURE LOG] Dotenv directory not found
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EnvLoader] DotenvException: .env Not found");
            }

        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Handle illegal argument exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EnvLoader] IllegalArgumentException: " + e.getMessage());
        } catch (NullPointerException e) {
            // [FAILURE LOG] Handle null pointer exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EnvLoader] NullPointerException: " + e.getMessage());
        } catch (DotenvException e) {
            // [FAILURE LOG] Handle dotenv exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EnvLoader] DotenvException: Erro ao carregar o arquivo .env");
        } catch (Exception e) {
            // [FAILURE LOG] Handle generic exception
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [EnvLoader] Exception: " + e.getMessage());
        }
    }

    // [DATA ACCESS] Safely retrieves the /WEB-INF directory path
    public static Optional<Path> getDirectoryPath(ServletContext servletContext) {
        Path webInfPath = Path.of(servletContext.getRealPath("/WEB-INF"));
        return Optional.of(webInfPath);
    }

    // [DATA ACCESS] Returns the loaded Dotenv instance
    public static Dotenv getDotenv() {
        return dotenv;
    }
}
