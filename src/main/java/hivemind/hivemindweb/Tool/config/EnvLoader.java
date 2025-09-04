package hivemind.hivemindweb.Tool.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./") // Root config
            .filename(".env")
            .load();

    //Return Dotenv class for get infos with dotenv class
    public static Dotenv getDotenv() {
        return dotenv;
    }
}
