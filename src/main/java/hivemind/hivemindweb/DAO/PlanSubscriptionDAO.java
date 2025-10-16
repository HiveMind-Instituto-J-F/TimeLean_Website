<<<<<<< HEAD
package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.PlanSubscription;

public class PlanSubscriptionDAO {
    public static List<PlanSubscription> select(PlanSubscription planSubscription){
        List<PlanSubscription> PlanSubscriptionList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN_SUBSCRIPTION ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PlanSubscription planSubscriptionLocal = new PlanSubscription(
                    rs.getInt("id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getString("cnpj_company"),
                    rs.getInt("id_plan")
                );
                PlanSubscriptionList.add(planSubscriptionLocal);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return PlanSubscriptionList;
    }

    public static int getID(PlanSubscription planSubscription){
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM PLAN_SUBSCRIPTION ORDER BY id";
        int id = 0;
        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if(rs.next()){
                id = rs.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return id;
    }
    
    public static boolean delete(PlanSubscription plansSubscription) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM PLAN_SUBSCRIPTION WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plansSubscription.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete" + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(PlanSubscription planSubscription) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE PLAN_SUBSCRIPTION
            SET start_date = ?,
                cnpj_company = ?,
                id_plan = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDate(1,Date.valueOf(planSubscription.getStartDate()));
            pstm.setString(2,planSubscription.getCnpjCompany());
            pstm.setInt(3,planSubscription.getIdPlan());
            pstm.setInt(4, planSubscription.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(PlanSubscription planSubscription, Boolean hasId){
        DBConnection db = new DBConnection();
        String sql = """
            INSERT INTO PLAN_SUBSCRIPTION (id, start_date, cnpj_company, id_plan)
            VALUES (?,?, ?, ?)
        """;

        if (hasId){
            try(Connection conn = db.connected();
                PreparedStatement psmt = conn.prepareStatement(sql);){
                psmt.setInt(1,planSubscription.getId());
                psmt.setDate(2, Date.valueOf(planSubscription.getStartDate()));
                psmt.setString(3, planSubscription.getCnpjCompany());
                psmt.setInt(4, planSubscription.getIdPlan());

                return psmt.executeUpdate() > 0;
            }catch (SQLException sqle) {
                System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
                return false;
            }
        }

        sql = """
            INSERT INTO PLAN_SUBSCRIPTION (start_date, cnpj_company, id_plan)
            VALUES (?, ?, ?)
        """;

        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setDate(1, Date.valueOf(planSubscription.getStartDate()));
            psmt.setString(2, planSubscription.getCnpjCompany());
            psmt.setInt(3, planSubscription.getIdPlan());

            return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
            return false;
        }
    }
}
=======
package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.PlanSubscription;

public class PlanSubscriptionDAO {
    public static List<PlanSubscription> select(PlanSubscription planSubscription){
        List<PlanSubscription> PlanSubscriptionList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN_SUBSCRIPTION ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                PlanSubscription planSubscriptionLocal = new PlanSubscription(
                    rs.getInt("id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getString("cnpj_company"),
                    rs.getInt("id_plan")
                );
                PlanSubscriptionList.add(planSubscriptionLocal);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return PlanSubscriptionList;
    }

    public static int getID(PlanSubscription planSubscription){
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM PLAN_SUBSCRIPTION ORDER BY id";
        int id = 0;
        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            if(rs.next()){
                id = rs.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return id;
    }
    
    public static boolean delete(PlanSubscription plansSubscription) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM PLAN_SUBSCRIPTION WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, plansSubscription.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete" + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(PlanSubscription planSubscription) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE PLAN_SUBSCRIPTION
            SET start_date = ?,
                cnpj_company = ?,
                id_plan = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDate(1,Date.valueOf(planSubscription.getStartDate()));
            pstm.setString(2,planSubscription.getCnpjCompany());
            pstm.setInt(3,planSubscription.getIdPlan());
            pstm.setInt(4, planSubscription.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(PlanSubscription planSubscription, Boolean hasId){
        DBConnection db = new DBConnection();
        String sql = """
            INSERT INTO PLAN_SUBSCRIPTION (id, start_date, cnpj_company, id_plan)
            VALUES (?,?, ?, ?)
        """;

        if (hasId){
            try(Connection conn = db.connected();
                PreparedStatement psmt = conn.prepareStatement(sql);){
                psmt.setInt(1,planSubscription.getId());
                psmt.setDate(2, Date.valueOf(planSubscription.getStartDate()));
                psmt.setString(3, planSubscription.getCnpjCompany());
                psmt.setInt(4, planSubscription.getIdPlan());

                return psmt.executeUpdate() > 0;
            }catch (SQLException sqle) {
                System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
                return false;
            }
        }

        sql = """
            INSERT INTO PLAN_SUBSCRIPTION (start_date, cnpj_company, id_plan)
            VALUES (?, ?, ?)
        """;

        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setDate(1, Date.valueOf(planSubscription.getStartDate()));
            psmt.setString(2, planSubscription.getCnpjCompany());
            psmt.setInt(3, planSubscription.getIdPlan());

            return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
            return false;
        }
    }
}
>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
