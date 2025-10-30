package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Admin;

public class AdminDAO {
    public static List<Admin> select(){
        DBConnection db = new DBConnection();
        List<Admin> adminsList = new ArrayList<>();
        String sql = "SELECT id, email, password FROM admin ORDER BY id";
        
        try (Connection conn = db.connected();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            
            while(rs.next()){
                Admin adminLocal = new Admin(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                adminsList.add(adminLocal);
            }
         } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select " + e.getMessage() + "\n");
        }
        return adminsList;
    }

    public static Admin selectByEmail(String email){
        DBConnection db = new DBConnection();
        String sql = "SELECT id, email, password FROM admin WHERE email = ?";

        try (Connection conn = db.connected();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, email);
            try (ResultSet rs = psmt.executeQuery()) {
                if(rs.next()){ //get one line
                    return new Admin(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed in selectByEmail: " + e.getMessage());
        }

        return null;
    }
}
