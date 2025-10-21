package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plan;

public class PlanDAO {
    public static boolean insert(Plan plan, boolean hasId) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plan (id, name, description, price, duration) " +
                "VALUES (?,?,?,?,?)";

        if(hasId){
            try (Connection conn = db.connected();
                 PreparedStatement pstm = conn.prepareStatement(sql)) {

                pstm.setInt(1, plan.getId());
                pstm.setString(2, plan.getName());
                pstm.setString(3, plan.getDescription());
                pstm.setDouble(4,plan.getPrice());
                pstm.setInt(5,plan.getDuration());

                return pstm.executeUpdate() > 0;

            } catch (SQLException sqle) {
                System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
            }
            return false;
        }

        sql = "INSERT INTO plan (name, description, price, duration) " +
                "VALUES (?,?,?,?)";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, plan.getName());
            pstm.setString(2, plan.getDescription());
            pstm.setDouble(3,plan.getPrice());
            pstm.setInt(4,plan.getDuration());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
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
            System.err.println("[ERROR] Falied in Update: " + sqle.getMessage());
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
            System.err.println("[ERROR] Falied in delete: " + sqle.getMessage());
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
            System.err.println("[ERROR] Falied in select" + e.getMessage());
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
            System.err.println("[ERROR] Falied in select: " + sqle.getMessage());
        }
        return null;
    }

    public static Plan selectByID(int id){
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN WHERE NAME = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()){
                return new Plan(rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("DESCRIPTION"), rs.getInt("DURATION"),
                        rs.getDouble("PRICE"));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in select: " + sqle.getMessage());
        }
        return null;
    }

    public static double getPrice(int id_plan){
        DBConnection db = new DBConnection();
        String sql = "SELECT price FROM Plan WHERE id=?;";
        double price = 0.0;
        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setInt(1, id_plan);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                }
            }
        }catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return price;
    }

    public static String getName(int id){
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM Plan WHERE id=?;";
        String nameDB = "";
        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    nameDB = rs.getString("name");
                }
            }
        }catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return nameDB;
    }

    public static String getName(String name){
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM Plan WHERE id=?;";
        String nameDB = "";
        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setString(1, name);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    nameDB = rs.getString("name");
                }
            }
        }catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return nameDB;
    }

    public static int getID(int id){
        DBConnection db = new DBConnection();
        String sql = "SELECT id AS idDB FROM Plan WHERE id=?;";
        int idDB = 0;
        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    idDB = rs.getInt("idDB");
                }
            }
        }catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return idDB;
    }

    public static boolean getAction(int id) {
        DBConnection db = new DBConnection();
        String sql = "SELECT is_active FROM Plan WHERE id=?;";
        boolean is_active = false;

        try (Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_active = rs.getBoolean("is_active");
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Failed in select: " + sqle.getMessage());
        }

        return is_active;
    }

    public static boolean setActive(Plan planLocal) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE plan SET is_active = ? WHERE id = ?;";

        try (Connection conn = db.connected();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setBoolean(1, planLocal.getActive());
            pstm.setInt(2, planLocal.getId());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Failed to update: " + sqle.getMessage());
            return false;
        }
    }

    public static boolean setActiveFalse(Plan planLocal) {
        return false;
    }

}
