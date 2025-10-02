package hivemind.hivemindweb.config;

import java.util.Optional;
import java.nio.file.Path;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;

public class EnvLoader {
    private static Dotenv dotenv = null;
    private static final String nameFile = ".env";

    @PostConstruct //Are createad class, the contrutor are exec
    public static void init(ServletContext servletContext){
        try{
            Optional<Path> dir = EnvLoader.getDiretory(servletContext);
            if (dir.isPresent()) {
                dotenv = Dotenv.configure()
                        .ignoreIfMissing()
                        .directory(dir.get().toString()) // get Path Of .env
                        .filename(EnvLoader.nameFile)
                        .load();
            } else {
                System.out.println("[ERROR] DotEnv not found");
            }
        }catch(DotenvException IOe){
            System.out.println("[ERROR] Error in get File of DotEnv");
        }
    }

    // Returns Optional<Path> for /WEB-INF directory, handling null safely with ofNullable
    public static Optional<Path> getDiretory(ServletContext servletContext){
        Path pathLocal = Path.of(servletContext.getRealPath("/WEB-INF"));
        return Optional.ofNullable(pathLocal);
    }

    //Return Dotenv class for get infos with dotenv class
    public static Dotenv getDotenv() {
        return dotenv;
    }
}
