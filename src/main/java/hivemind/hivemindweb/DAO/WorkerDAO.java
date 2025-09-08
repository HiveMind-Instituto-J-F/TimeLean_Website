package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Tool.Tool;
import hivemind.hivemindweb.models.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {
    public static boolean insert(String CPF, String name, String lastName, String sector, String role,int id){
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO trabalhador VALUES (?,?,?,?,?,?)";
        try(Connection conn = db.connected()){ // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setString(2, CPF);
            pstm.setString(3, name);
            pstm.setString(4, lastName);
            pstm.setString(5, sector);
            pstm.setString(6, role);

            return pstm.executeUpdate() > 0;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    public static List<Worker> select() {
        List<Worker> workers = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM trabalhador";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Worker workerLocal = new Worker(
                        rs.getInt("id")
                        //Wait for create DB colums
//                        rs.getString("name"),
//                        rs.getString("lastName"),
//                        rs.getString("password"),
//                        rs.getString("sector"),
//                        rs.getString("profileType")
                );
                workers.add(workerLocal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }


    public static boolean update(String column, String value, int id) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE trabalhador SET " + column + " = ? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value); // só o valor vai como placeholder
            stmt.setInt(2, id);

            return stmt.executeUpdate() >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean delete(String CPF){
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM Plant WHERE CPF = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CPF);
            return pstmt.executeUpdate() >= 0;
        }catch (Exception sqle){
            sqle.printStackTrace();
        }
        return false;
    }
}
