package test;

import hivemind.hivemindweb.Connection.DBConnection;

import java.sql.Connection;

public class TestEnv {
    public static void main(String[] args) {
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
