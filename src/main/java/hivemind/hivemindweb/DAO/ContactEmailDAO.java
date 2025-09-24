package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.ContactEmail;
import hivemind.hivemindweb.models.PlanSubscription;

public class ContactEmailDAO {
    public static List<ContactEmail> select(ContactEmailDAO contactEmail){
        List<ContactEmail> ContactEmailList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM contact_email ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContactEmail contactEmailLocal = new ContactEmail(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("cnpj_company")
                );  
                ContactEmailList.add(contactEmailLocal);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }

        return ContactEmailList;
    }
    public static boolean delete(ContactEmail contactEmail) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM contact_email WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, contactEmail.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete" + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(ContactEmail contactEmail) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE contact_email
            SET email = ?,
                cnpj_company = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, contactEmail.getEmail());
                pstm.setString(2, contactEmail.getCompany());
                pstm.setInt(3, contactEmail.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(ContactEmail contactEmail){
        DBConnection db = new DBConnection();
        String sql = """
            INSERT INTO contact_email (id,email,cnpj_company)
            VALUES (?,?,?)
        """;

        try(Connection conn = db.connected();
             PreparedStatement psmt = conn.prepareStatement(sql);){
                psmt.setInt(1, contactEmail.getId());
                psmt.setString(2, contactEmail.getEmail());
                psmt.setString(3, contactEmail.getCompany());
                return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
        }
        return false;

    }
}
