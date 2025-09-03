package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;

import java.sql.*;

public class TestDAO {
    //Demo
    public static boolean insert(){
        Connection con = null;
        try {
            DBConnection db = new DBConnection();
            return true;
        }catch (Exception SQLE){
            SQLE.printStackTrace();
        }
        return false;
    }
}
