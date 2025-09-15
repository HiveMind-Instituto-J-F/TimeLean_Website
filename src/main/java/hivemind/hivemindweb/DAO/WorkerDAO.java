package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Company;
import hivemind.hivemindweb.models.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {
    public static boolean insert(Worker worker){
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO worker VALUES (?,?,?,?,?,?)";
        try(Connection conn = db.connected()){ // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setLong(1, worker.getId());
            pstm.setString(2, worker.getCPF());
            pstm.setString(3, worker.getName());
            pstm.setString(4, worker.getLastName());
            pstm.setString(5, worker.getPassword());
            pstm.setString(6, worker.getProfileType()); //Refatorar models

            return pstm.executeUpdate() > 0;
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }

    public static List<Worker> select() {
        List<Worker> workers = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM worker";

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
            return workers;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in select: " + sqle.getMessage());
        }

        return workers;
    }


    public static boolean update(Worker worker) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE worker SET CPF = ? role=? , name=? , last_name=?, sector=?, email=?,login_password=? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, worker.getCPF());
            pstm.setString(3, worker.getName());
            pstm.setString(4, worker.getLastName());
            pstm.setString(5, worker.getSector());
            pstm.setString(7, worker.getProfileType());
            pstm.setLong(8, worker.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Error In Update, Erro: " + sqle.getMessage());
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
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }
}
