package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Worker;

public class WorkerDAO {

    // [DATA ACCESS] Select all workers
    public static List<Worker> select() {
        List<Worker> workersList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM worker";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Worker workerFromDb = mapResultSetToWorker(rs);
                workersList.add(workerFromDb);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO select: " + e.getMessage());
        }

        return workersList;
    }

    // [DATA ACCESS] Select worker by CPF
    public static Worker selectByCpf(String cpf) {
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM worker WHERE CPF = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToWorker(rs);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO selectByCpf: " + e.getMessage());
        }

        return null;
    }

    // [DATA ACCESS] Insert new worker
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
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO insert: " + e.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Update worker
    public static boolean update(Worker worker) {
        DBConnection db = new DBConnection();
        String sql = """
                UPDATE worker
                   SET role = ?,
                       sector = ?,
                       name = ?,
                       login_email = ?,
                       login_password = ?
                 WHERE cpf = ?
                """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, worker.getRole());
            pstm.setString(2, worker.getSector());
            pstm.setString(3, worker.getName());
            pstm.setString(4, worker.getLoginEmail());
            pstm.setString(5, worker.getLoginPassword());
            pstm.setString(6, worker.getCpf());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO update: " + e.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Delete worker
    public static boolean delete(String cpf) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM worker WHERE CPF = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO delete: " + e.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Select workers with filters
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
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, companyCnpj);
            if (filterType == FilterType.Worker.CPF || filterType == FilterType.Worker.SECTOR) {
                pstm.setString(2, filter);
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Worker workerFromDb = mapResultSetToWorker(rs);
                workersList.add(workerFromDb);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] WorkerDAO selectFilter: " + e.getMessage());
        }

        return workersList;
    }

    // [BUSINESS RULES] Map ResultSet to Worker object
    private static Worker mapResultSetToWorker(ResultSet rs) throws SQLException {
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
}
