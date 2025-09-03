package hivemind.hivemindweb.DAO;

import java.sql.*;
import hivemind.hivemindweb.Connection.DBConnection;

public class EmpresaDAO {
    public static boolean insert(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            System.out.println((conn));
            return true;
        }
        catch (Exception sqle) { // Classe de teste temporária usada apenas para testes e push antes do merge
            sqle.printStackTrace();
            return false;
        }
    }

    public static boolean update(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE tableTest SET (?) WHERE (?) = (?)");
            pstmt.setString(1, "hivemind");
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static boolean delete(){
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
