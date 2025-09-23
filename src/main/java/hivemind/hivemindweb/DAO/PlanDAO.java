package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    public static boolean insert(Plan plan) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plan (id, name, description, reports_limit, plants_limit, price, duration) " +
                "VALUES (?,?,?,?,?,?,?)";


        try (Connection conn = db.connected();

             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plan.getId());
            pstm.setString(2, plan.getName());
            pstm.setString(3, plan.getDescription());
            pstm.setInt(4, plan.getReportsLimit());
            pstm.setInt(5, plan.getPlantsLimit());
            pstm.setDouble(6,plan.getPrice());
            pstm.setInt(7,plan.getDuration());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Plan plan) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE plans SET name = ?, reports_limit=?, plants_limit=?,description=? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, plan.getName());
            pstm.setInt(2, plan.getReportsLimit());
            pstm.setInt(3, plan.getPlantsLimit());
            pstm.setString(4, plan.getDescription());
            pstm.setInt(5, plan.getId());

            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Plan plan) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM plan WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plan.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete" + sqle.getMessage());
        }
        return false;
    }

    public static List<Plan> select() {
        List<Plan> plans = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plan ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plan planLocal = new Plan(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("duration"),
                        rs.getDouble("price"),
                        rs.getInt("reports_limit"),
                        rs.getInt("plants_limit")
                );
                plans.add(planLocal);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }

        return plans;
    }
}
