package hivemind.hivemindweb.Tool;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// Esta classe e temporaria mais seu conceito deve ser mantido sendo a Idea de manter funçoes statics ultilirias globlais   

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import hivemind.hivemindweb.models.Admin;

public class Tool {
    public static boolean binarySeach(List<Admin> adminsList, Admin admin){
        try{
            int low = 0;
            int high = (adminsList.size() - 1);
            
            while (low <= high) {
                int medio = (low + high) / 2;
                Admin point = adminsList.get(medio);                
            }
        }catch(NullPointerException npe){
            System.out.println("[ERROR] Null Point Error, Erro: " + npe.getMessage());
        }
        return true;
    }

    public static String hash(String password) throws IOException {
        if (password == null){
            throw new IOException("password must not be null");
        }
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password.trim(), salt);

        return hash;
    }

    public static boolean matchHash(String password, String hash) {
        if (password == null || hash == null) {
            return false;
        }
        String cleanPassword = password.trim();
        return BCrypt.checkpw(cleanPassword, hash);
    }
}
