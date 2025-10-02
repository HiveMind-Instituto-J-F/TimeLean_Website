package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    public static boolean insert(Plan plan) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plan (id, name, description, price, duration) " +
                "VALUES (?,?,?,?,?,?,?)";


        try (Connection conn = db.connected();

             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plan.getId());
            pstm.setString(2, plan.getName());
            pstm.setString(3, plan.getDescription());
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
        String sql = "UPDATE plan SET name = ?,description=? WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, plan.getName());
            pstm.setString(4, plan.getDescription());
            pstm.setInt(5, plan.getId());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in Update" + sqle.getMessage());
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
        List<Plan> plansList = new ArrayList<>();
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
                        rs.getDouble("price")
                );
                plansList.add(planLocal);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }
        return plansList;
    }

    public static Plan select(String name){
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN WHERE NAME = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, name);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()){
                return new Plan(rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("DESCRIPTION"), rs.getInt("DURATION"),
                        rs.getDouble("PRICE"));
            }

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in select" + sqle.getMessage());
        }
        return null;
    }
}
