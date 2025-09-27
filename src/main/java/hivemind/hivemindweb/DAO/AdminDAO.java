package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Tool.Tool;
import hivemind.hivemindweb.models.Admin;

public class AdminDAO {
    public static List<Admin> select(){
        DBConnection db = new DBConnection();
        List<Admin> adminsList = new ArrayList<>();
        String sql = "SELECT * FROM admin ODER BY id";
        
        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()){
                Admin adminLocal = new  Admin(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("hashPassword")
                );
                adminsList.add(adminLocal);
            }
         } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }
        return adminsList;
    }

    public static boolean login(Admin admin){
        List<Admin> adminsList = AdminDAO.select(); // Get Data
        Admin adminClient = Tool.binarySeach(adminsList, admin); // Get Class of List 
        if(adminClient == null){return false;} 

        return Tool.matchHash(admin.getHashPassword(), adminClient.getHashPassword());
    }
}
