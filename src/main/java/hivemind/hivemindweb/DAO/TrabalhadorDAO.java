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

    public static boolean update(){
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
}
