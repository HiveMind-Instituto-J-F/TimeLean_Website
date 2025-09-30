package hivemind.hivemindweb.config;

import java.util.Optional;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.annotation.PostConstruct;

public class EnvLoader {
    private static Dotenv dotenv = null;
    private static final String nameFile = ".env";

    @PostConstruct //Are createad class, the contrutor are exec
    public static void init(){
        try{
            Optional<Path> dir = EnvLoader.getDiretory();
            if (dir.isPresent()) {
                dotenv = Dotenv.configure()
                        .ignoreIfMissing()
                        .directory(dir.get().getParent().toString()) // get Path Of .env
                        .filename(EnvLoader.nameFile)
                        .load();
            } else {
                System.out.println("[ERROR] DotEnv not found");
            }
        }catch(DotenvException IOe){
            System.out.println("[ERROR] Error in get File of DotEnv");
        }
    }

    public static Optional<Path> getDiretory(){
        Optional<Path> dotEnvPath = Optional.of(Paths.get(".env").toAbsolutePath());
            if(dotEnvPath.isEmpty()){
                System.out.println("[ERROR] DotEnv not found or is null");
            }
            return dotEnvPath;
    }

    //Return Dotenv class for get infos with dotenv class
    public static Dotenv getDotenv() {
        return dotenv;
    }
}
