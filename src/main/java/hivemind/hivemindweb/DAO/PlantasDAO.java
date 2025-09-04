package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;

import java.sql.*;

public class PlantasDAO {
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

    public static boolean update(String responsible){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        String sql = "UPDATE Plantas SET responsible = ? WHERE responsible = ?";
        try {
            System.out.println((conn));
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

    public static ResultSet select(){
        DBConnection db = new DBConnection();
        Connection conn = db.connected();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Plant");
            ResultSet rs = stmt.executeQuery();
            conn.close();
            return rs;
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return null;
    }
}
