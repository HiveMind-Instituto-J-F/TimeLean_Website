package hivemind.hivemindweb.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Company;

public class CompanyDAO {
    public static boolean insert(Company company){
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO company (CNPJ, cnae, name, registrant_cpf) VALUES (?,?,?,?)";
        try(Connection conn = db.connected()){ // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, company.getName());
            pstm.setString(2, company.getCnae());
            pstm.setString(3, company.getName());
            pstm.setString(4, company.getRegistrantCpf());
            return pstm.executeUpdate() > 0;
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Company company) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE company SET CNPJ = ?, name = ?, cnae = ?, registrant_cpf = ? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, company.getCNPJ());
            pstm.setString(2, company.getName());
            pstm.setString(3, company.getCnae());
            pstm.setString(4, company.getRegistrantCpf());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static boolean delete(Company company) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM company WHERE CNPJ = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, company.getCNPJ());
            return pstmt.executeUpdate() >= 0;
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }

    public static List<Company> select() {
        List<Company> companysList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM company ORDER BY CNPJ";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Company companyLocal = new Company(
                        //Wait for DB colums is create
                        rs.getString("CNPJ"),
                        rs.getString("name"),
                        rs.getString("cnae"),
                        rs.getString("registrant_cpf")
                );
                companysList.add(companyLocal);
            }
        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in select: " + sqle.getMessage());
        }

        System.out.println("[DEBUG] In select EmrpesaDAO ,Companys found: " + companysList.size() +  " data: " + companysList);

        return companysList;
    }
}
