package hivemind.hivemindweb.DAO;

import java.sql.*;
import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Company;

public class EmpresaDAO {
    public static boolean insert(Company company){
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO empresa VALUES (?,?,?,?,?,?)";
        try(Connection conn = db.connected()){ // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setLong(1, company.getId());
            pstm.setString(2,company.getCompanyName());
            pstm.setString(3,company.getCNPJ());
//            pstm.setString(4,company.get());
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
            PreparedStatement pstmt = conn.prepareStatement("UPDATE tableTest SET (?) WHERE (?) = (?)");
            pstmt.setString(1, "hivemind");
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String CNPJ){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        String sql = "DELETE FROM Enterprise WHERE id = (?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,CNPJ);
            return true;
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    // Admin Meths
    public static ResultSet select(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Enterprise");
            ResultSet rs = stmt.executeQuery();
            conn.close();
            return rs;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return null;
    }
}
