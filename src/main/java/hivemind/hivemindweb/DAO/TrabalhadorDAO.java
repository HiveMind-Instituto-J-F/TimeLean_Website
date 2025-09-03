package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrabalhadorDAO {
    public static boolean insert(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            System.out.println((conn));
            return true;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static ResultSet select(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Works");
            ResultSet rs = stmt.executeQuery();
            conn.close();
            return rs;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return null;
    }

    //
    public static boolean update(){
        DBConnection db = new DBConnection();
        String sql = "UPDATE ? SET ? WHERE ?";
        try(Connection conn = db.connected()) {
            return true;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String CPF){
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM Plant WHERE CPF = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CPF);
            int rowsAffects = pstmt.executeUpdate(); // return rowAffects
            conn.close();
            return rowsAffects >= 0;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }
}
