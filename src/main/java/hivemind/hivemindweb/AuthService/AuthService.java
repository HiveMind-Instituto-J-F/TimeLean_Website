package hivemind.hivemindweb.AuthService;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;

public class AuthService{
    public static boolean login(Admin admin){
        Admin adminClient =  AdminDAO.selectByEmail(admin.getEmail());
        if(adminClient == null){return false;} 

        return AuthService.matchHash(admin.getHashPassword(), adminClient.getHashPassword());
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