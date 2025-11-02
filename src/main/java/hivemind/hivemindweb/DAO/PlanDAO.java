package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plan;

public class PlanDAO {

    // [DATA ACCESS] Insert a new plan
    public static boolean insert(Plan plan, boolean hasId) {
        DBConnection dbConnection = new DBConnection();
        String sqlWithId = "INSERT INTO plan (id, name, description, price, duration) VALUES (?,?,?,?,?)";
        String sqlWithoutId = "INSERT INTO plan (name, description, price, duration) VALUES (?,?,?,?)";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(hasId ? sqlWithId : sqlWithoutId)) {

            if (hasId) {
                pstmt.setInt(1, plan.getId());
                pstmt.setString(2, plan.getName());
                pstmt.setString(3, plan.getDescription());
                pstmt.setDouble(4, plan.getPrice());
                pstmt.setInt(5, plan.getDuration());
            } else {
                pstmt.setString(1, plan.getName());
                pstmt.setString(2, plan.getDescription());
                pstmt.setDouble(3, plan.getPrice());
                pstmt.setInt(4, plan.getDuration());
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO insert: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Update a plan
    public static boolean update(Plan plan) {
        DBConnection dbConnection = new DBConnection();
        String sql = "UPDATE plan SET name = ?, description = ?, price = ?, duration = ?, is_active = ? WHERE id = ?";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plan.getName());
            pstmt.setString(2, plan.getDescription());
            pstmt.setDouble(3, plan.getPrice());
            pstmt.setInt(4, plan.getDuration());
            pstmt.setBoolean(5, plan.isActive());
            pstmt.setInt(6, plan.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO update: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Select all plans
    public static List<Plan> select() {
        List<Plan> plansList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan ORDER BY id";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                plansList.add(mapResultSetToPlan(rs));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO select: " + sqle.getMessage());
        }

        return plansList;
    }

    // [DATA ACCESS] Select plan by name
    public static Plan selectByName(String paramName) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan WHERE name = ?";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paramName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlan(rs);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO selectByName: " + sqle.getMessage());
        }

        return null;
    }

    // [DATA ACCESS] Select plan by ID
    public static Plan selectByID(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan WHERE id = ?";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlan(rs);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO selectByID: " + sqle.getMessage());
        }

        return null;
    }

    // [DATA ACCESS] Get price by plan ID
    public static double getPrice(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT price FROM plan WHERE id = ?";
        double price = 0.0;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO getPrice: " + sqle.getMessage());
        }

        return price;
    }

    // [DATA ACCESS] Get name by plan ID
    public static String getName(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT name FROM plan WHERE id = ?";
        String nameFromDb = "";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nameFromDb = rs.getString("name");
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanDAO getName: " + sqle.getMessage());
        }

        return nameFromDb;
    }

    // [BUSINESS RULES] Map ResultSet to Plan object
    private static Plan mapResultSetToPlan(ResultSet rs) throws SQLException {
        return new Plan(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getDouble("price"),
                rs.getBoolean("is_active")
        );
    }
}
