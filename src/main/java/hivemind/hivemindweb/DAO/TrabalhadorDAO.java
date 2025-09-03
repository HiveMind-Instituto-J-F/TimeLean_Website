package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;

import java.sql.Connection;

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

    public static boolean select(){
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
