package hivemind.hivemindweb.Connection;

import java.sql.*;

public class DBConnection {
    public Connection connected() {
        Connection con = null;
        try {
            String url = "jdbc:postgresql://localhost:5242/PooDB";
            String user = "quitto";
            String password = "123";

            con = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException E){
            E.printStackTrace();
        }
    return con;
    }

    public void desconect(Connection con) {
        try {
            if (con != null && !con.isClosed()){con.close();}
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }}
