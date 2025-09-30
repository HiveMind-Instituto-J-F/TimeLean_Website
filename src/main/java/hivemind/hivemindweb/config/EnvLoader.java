package hivemind.hivemindweb.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            // .directory("./") // Root config
            .directory("C:\\Users\\gustavoquitto-ieg\\OneDrive - Instituto J&F\\Área de Trabalho\\Projetcs\\Tech\\HivemindBackend\\src\\main\\webapp\\WEB-INF\\")
            .filename(".env")
            .load();

    //Return Dotenv class for get infos with dotenv class
    public static Dotenv getDotenv() {
        return dotenv;
    }
}