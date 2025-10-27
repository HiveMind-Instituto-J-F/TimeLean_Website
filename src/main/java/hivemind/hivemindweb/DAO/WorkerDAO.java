package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Worker;

public class WorkerDAO {

    public static List<Worker> select() {
        List<Worker> workersList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM worker";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Worker worker = new Worker(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("role"),
                        rs.getString("sector"),
                        rs.getString("name"),
                        rs.getString("login_email"),
                        rs.getString("login_password"),
                        rs.getString("cnpj_plant")
                );
                workersList.add(worker);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in select: " + e.getMessage());
        }

        return workersList;
    }

    public static Worker selectByCpf(String cpf) {
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM worker WHERE CPF = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Worker(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("role"),
                        rs.getString("sector"),
                        rs.getString("name"),
                        rs.getString("login_email"),
                        rs.getString("login_password"),
                        rs.getString("cnpj_plant")
                );
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in selectByCpf: " + e.getMessage());
        }
        return null;
    }

    public static boolean insert(Worker worker) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO worker (cpf, role, sector, name, login_email, login_password, cnpj_plant) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, worker.getCpf());
            pstm.setString(2, worker.getRole());
            pstm.setString(3, worker.getSector());
            pstm.setString(4, worker.getName());
            pstm.setString(5, worker.getLoginEmail());
            pstm.setString(6, worker.getLoginPassword());
            pstm.setString(7, worker.getCnpjPlant());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in insert: " + e.getMessage());
        }
        return false;
    }

    public static boolean update(Worker worker, String oldCpf) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE worker
               SET cpf = ?,
                   role = ?,
                   sector = ?,
                   name = ?,
                   login_email = ?,
                   login_password = ?,
                   cnpj_plant = ?
             WHERE cpf = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, worker.getCpf());
            pstm.setString(2, worker.getRole());
            pstm.setString(3, worker.getSector());
            pstm.setString(4, worker.getName());
            pstm.setString(5, worker.getLoginEmail());
            pstm.setString(6, worker.getLoginPassword());
            pstm.setString(7, worker.getCnpjPlant());
            pstm.setString(8, oldCpf);

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in update: " + e.getMessage());
        }
        return false;
    }

    public static boolean delete(String cpf) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM worker WHERE CPF = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in delete: " + e.getMessage());
        }
        return false;
    }

    public static List<Worker> selectFilter(FilterType.Worker filterType, String filter, String companyCnpj) {
        List<Worker> workersList = new ArrayList<>();
        DBConnection db = new DBConnection();

        String sql;
        if (filterType == FilterType.Worker.CPF) {
            sql = "SELECT * FROM worker WHERE CNPJ_PLANT = ? AND CPF = ?";
        } else if (filterType == FilterType.Worker.SECTOR){
            sql = "SELECT * FROM worker WHERE CNPJ_PLANT = ? AND SECTOR = ?";
        } else {
            sql = "SELECT * FROM worker WHERE CNPJ_PLANT = ?";
        }

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setString(1, companyCnpj);
            if (filterType == FilterType.Worker.CPF || filterType == FilterType.Worker.SECTOR) {
                pstm.setString(2, filter);
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Worker worker = new Worker(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("role"),
                        rs.getString("sector"),
                        rs.getString("name"),
                        rs.getString("login_email"),
                        rs.getString("login_password"),
                        rs.getString("cnpj_plant")
                );
                workersList.add(worker);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in select: " + e.getMessage());
        }

        return workersList;
    }
}
