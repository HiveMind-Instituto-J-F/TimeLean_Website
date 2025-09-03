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
}
