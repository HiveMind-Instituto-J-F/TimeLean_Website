package hivemind.hivemindweb.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Plans;

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
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Company company) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE company SET CNPJ = ?, companyName = ?, companyType = ?, registrantName = ?, registrantLastName = ?, registrantEmail = ?, function = ?, password = ?, CPF = ? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, company.getCNPJ());
            pstm.setString(2, company.getCompanyName());
            pstm.setString(3, company.getCompanyType());
            pstm.setString(4, company.getRegistrantName());
            pstm.setString(5, company.getRegistrantLastName());
            pstm.setString(6, company.getRegistrantEmail());
            pstm.setString(7, company.getFunction());
            pstm.setString(8, company.getPassword());
            pstm.setLong(9, company.getCPF());
            pstm.setLong(10, company.getId());

            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
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
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in delete: " + sqle.getMessage());
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
        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in select: " + sqle.getMessage());
        }

        System.out.println("[DEBUG] In select EmrpesaDAO ,Companys found: " + companys.size() +  " data: " + companys);

        return companys;
    }
}
