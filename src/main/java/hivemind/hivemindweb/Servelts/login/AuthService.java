package hivemind.hivemindweb.Servelts.login;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    public static Admin login(String email, String password) {
        try {
            Admin adminFromDB = AdminDAO.selectByEmail(email);
            if (adminFromDB != null) {
                if (BCrypt.checkpw(password, adminFromDB.getHashPassword())) {
                    return adminFromDB;
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] AuthService.login: " + e.getMessage());
        }
        return null;
    }
}
