package hivemind.hivemindweb.config;

import java.util.Optional;
import java.nio.file.Paths;
import java.nio.file.Path;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class EnvLoader {
    private static Dotenv dotenv = null;
    private static final String nameFile = ".env";

    public static void init(){
        try{

            String path = EnvLoader.getDiretory().toString();
            if(path == null){return;}
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .directory(path) // get Paht Of .env
                    .filename(EnvLoader.nameFile)
                    .load();
        }catch(DotenvException IOe){
            System.out.println("[ERROR] Error in get File of DotEnv");
        }
    }

    public static Optional<Path> getDiretory(){
        Optional<Path> dotEnvPath = Optional.of(Paths.get(".env").toAbsolutePath());
            if(dotEnvPath.isEmpty()){
                System.out.println("[ERROR] DotEnv not found or is null");
                return null; //Is not possible returns outher type
            }
            return dotEnvPath;
    }

    //Return Dotenv class for get infos with dotenv class
    public static Dotenv getDotenv() {
        return dotenv;
    }
}