package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plans;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlansDAO {
    public static boolean insert(Plans plan) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plan VALUES (?,?,?,?,?,?)";

        try (Connection conn = db.connected();

             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plan.getId());
            pstm.setString(2, plan.getName());
            pstm.setString(3, plan.getDescription());
            pstm.setInt(4, plan.getQntSales());
            pstm.setInt(5, plan.getDuration());
            pstm.setDouble(6, plan.getValue());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Plans plan) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE plans SET name = ?, description = ?, qntSales = ?, duration = ?, value = ? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, plan.getName());
            pstm.setString(2, plan.getDescription());
            pstm.setInt(3, plan.getQntSales());
            pstm.setInt(4, plan.getDuration());
            pstm.setDouble(5, plan.getValue());
            pstm.setInt(6, plan.getId());

            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(int id) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM plan WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete" + sqle.getMessage());
        }
        return false;
    }

    public static List<Plans> select() {
        List<Plans> plans = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plan ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plans planLocal = new Plans(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("qntSales"),
                        rs.getInt("duration"),
                        rs.getDouble("value")
                );
                plans.add(planLocal);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }

        return plans;
    }
}
