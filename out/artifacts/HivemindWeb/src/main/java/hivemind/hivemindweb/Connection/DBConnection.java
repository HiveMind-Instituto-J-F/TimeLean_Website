package hivemind.hivemindweb.Connection;

import hivemind.hivemindweb.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DBConnection {
    public Connection connected() {
        Connection con = null;
        try {
            Dotenv dotenv = EnvLoader.getDotenv();
            Class.forName("org.postgresql.Driver");

            String dbUrl = dotenv.get("db_host_name");
            String dbUser = dotenv.get("db_user");
            String dbPassword = dotenv.get("db_password");

            if(dbUrl == null || dbUser == null || dbPassword == null){
                System.out.println("[ERROR]: DB URL, DB USER, DB PASSWORD NULL");
                return null;
            }

            con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            System.out.println("[DEBUG] Conectado com sucesso!");
            return con;
        }
        catch (SQLException sqle){
            System.out.println("[ERROR] Erro Al Conetar: " + sqle.getMessage());
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("[ERROR] Error Class Not Found Exception: " + cnfe.getMessage());
        }
    return con;
    }

    public void desconect(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()){conn.close();}
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }}
