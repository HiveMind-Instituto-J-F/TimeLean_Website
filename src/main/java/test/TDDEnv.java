package test;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Tool.Tool;

import java.sql.Connection;

public class TDDEnv {
    public static void main(String[] args) {
        System.out.println(Tool.verifySQL("truncate"));
        Connection conn = null;
        DBConnection db = new DBConnection();
        try {
            conn = db.connected();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
