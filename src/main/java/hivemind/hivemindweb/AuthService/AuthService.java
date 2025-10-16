package hivemind.hivemindweb.AuthService;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;

public class AuthService{
    public static boolean login(Admin adminClient){
        System.out.println("[WARN] Join In Login");
        Admin adminDB =  AdminDAO.selectByEmail(adminClient.getEmail()); // get For DB
        if(adminDB == null){return false;}

        return BCrypt.checkpw(adminClient.getHashPassword(), adminDB.getHashPassword());
    }

    public static boolean login(String dbemail, String email, String password, String dbHashPassword) throws NullPointerException{
        if (dbemail.equals(email)){
            return BCrypt.checkpw(password, dbHashPassword);
        }
        return false;
    }

    public static String hash(String password) throws IOException {
        if (password == null){
            throw new IOException("password must not be null");
        }

        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password.trim(), salt);

        return hash;
    }
}