package hivemind.hivemindweb.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Company;

public class CompanyDAO {
    public static boolean insert(Company company){
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO company VALUES (?,?,?,?,?,?)";
        try(Connection conn = db.connected()){ // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setLong(1, company.getId());
            pstm.setString(2,company.getCompanyName());
            pstm.setString(3,company.getCNPJ());
//            pstm.setString(4,company.get()); role colums
            return pstm.executeUpdate() > 0;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static boolean update(Company companys){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE company SET (?) WHERE (?) = (?)");
            pstmt.setString(1, "hivemind");
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String CNPJ){
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM company WHERE CNPJ = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CNPJ);
            return pstmt.executeUpdate() >= 0;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static List<Company> select() {
        List<Company> companys = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM company ORDER BY CNPJ";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Company companyLocal = new Company(
                        //Wait for DB colums is create
                        rs.getString("CNPJ"),
                        rs.getString("companyName"),
                        rs.getString("companyType"),
                        rs.getString("registrantName"),
                        rs.getString("registrantLastName"),
                        rs.getString("registrantEmail"),
                        rs.getString("function"),
                        rs.getString("password"),
                        rs.getLong("id"),
                        rs.getLong("CPF")
                );
                companys.add(companyLocal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[DEBUG] In select EmrpesaDAO ,Companys found: " + companys.size() +  " data: " + companys);

        return companys;
    }
}
