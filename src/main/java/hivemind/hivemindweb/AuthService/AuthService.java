
package hivemind.hivemindweb.AuthService;
import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;

public class AuthService {

    // [PROCESS] Admin Login
    public static boolean login(Admin paramAdminClient) {
        // [DATA ACCESS] Retrieve admin from database by email
        Admin adminFromDb = AdminDAO.selectByEmail(paramAdminClient.getEmail());

        // [VALIDATION] Check if admin exists
        if (adminFromDb == null) return false;

        // [PROCESS] Compare hashed passwords
        return BCrypt.checkpw(paramAdminClient.getHashPassword(), adminFromDb.getHashPassword());
    }

    // [PROCESS] Generic Login
    public static boolean login(String paramEmailFromDb, String paramEmail, String paramPassword, String paramHashPasswordFromDb) {
        // [VALIDATION] Check for null values
        if (paramEmailFromDb == null) throw new IllegalArgumentException("Valor Nulo: 'email do banco de dados'");
        if (paramEmail == null) throw new IllegalArgumentException("Valor Nulo: 'email'");
        if (paramPassword == null) throw new IllegalArgumentException("Valor Nulo: 'senha'");
        if (paramHashPasswordFromDb == null) throw new IllegalArgumentException("Valor Nulo: 'senha criptografada do banco de dados'");

        // [PROCESS] Compare email and hashed password
        if (paramEmailFromDb.equals(paramEmail)) {
            return BCrypt.checkpw(paramPassword, paramHashPasswordFromDb);
        }

        return false;
    }

    // [PROCESS] Hashes password using BCrypt
    public static String hash(String paramPassword) throws IllegalArgumentException {
        // [VALIDATION] Check for null password
        if (paramPassword == null) throw new IllegalArgumentException("Valor Nulo: 'senha'");

        // [PROCESS] Generate salt and hash password
        String salt = BCrypt.gensalt();

        return BCrypt.hashpw(paramPassword.trim(), salt);
    }

    public static Admin login(String email, String password) {
        // [DATA ACCESS] Retrieve admin by email from database
        Admin adminFromDb = AdminDAO.selectByEmail(email);

        // [VALIDATION] Check if admin exists and password matches
        if (adminFromDb != null && BCrypt.checkpw(password, adminFromDb.getHashPassword())) {
            return adminFromDb;
        }
        return null;
    }

}
