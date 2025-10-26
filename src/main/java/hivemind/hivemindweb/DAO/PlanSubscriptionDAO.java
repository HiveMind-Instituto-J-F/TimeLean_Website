package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Plan;
import hivemind.hivemindweb.models.PlanSubscription;

public class PlanSubscriptionDAO {
    public static List<PlanSubscription> select(){
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
                    rs.getInt("id_plan"),
                    rs.getInt("number_installments"),
                    rs.getBoolean("status")
                );
                PlanSubscriptionList.add(planSubscriptionLocal);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }
        return PlanSubscriptionList;
    }

    public static PlanSubscription select(int id){
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN_SUBSCRIPTION WHERE ID = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                PlanSubscription planSubscriptionLocal = new PlanSubscription(
                        rs.getInt("id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getString("cnpj_company"),
                        rs.getInt("id_plan"),
                        rs.getInt("number_installments"),
                        rs.getBoolean("status")
                );
                return planSubscriptionLocal;
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return null;
    }

    public static List<PlanSubscription> selectFilter(FilterType.PlanSubscription filterType, String filter){
        List<PlanSubscription> planSubscriptionList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql;

        if (filterType == null || filterType == FilterType.PlanSubscription.ALL_VALUES) {
            sql = "SELECT * FROM plan_subscription";
        } else if (filterType == FilterType.PlanSubscription.ID_PLAN) {
            sql = "SELECT * FROM plan_subscription WHERE id_plan = ?";
        } else if (filterType == FilterType.PlanSubscription.CNPJ_COMPANY) {
            sql = "SELECT * FROM plan_subscription WHERE cnpj_company = ?";
        } else {
            return planSubscriptionList;
        }

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            if (filterType == FilterType.PlanSubscription.ID_PLAN) {
                pstm.setInt(1, Integer.parseInt(filter));
            } else if (filterType == FilterType.PlanSubscription.CNPJ_COMPANY) {
                pstm.setString(1, filter);
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                planSubscriptionList.add(new PlanSubscription(
                        rs.getInt("id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getString("cnpj_company"),
                        rs.getInt("id_plan"),
                        rs.getInt("number_installments"),
                        rs.getBoolean("status")
                ));
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in selectFilter: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format in filter: " + filter);
        }

        return planSubscriptionList;
    }

    public static List<PlanSubscription> selectActivePlans(String cnpjCompany) {
        List<PlanSubscription> planSubscriptionList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM PLAN_SUBSCRIPTION WHERE CNPJ_COMPANY = ? AND status = true";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, cnpjCompany);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    planSubscriptionList.add(new PlanSubscription(
                            rs.getInt("id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getString("cnpj_company"),
                            rs.getInt("id_plan"),
                            rs.getInt("number_installments"),
                            rs.getBoolean("status")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed in select: " + e.getMessage());
        }
        return planSubscriptionList;
    }
    
    public static boolean delete(int id) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM PLAN_SUBSCRIPTION WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
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
                status = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDate(1,Date.valueOf(planSubscription.getStartDate()));
            pstm.setBoolean(2, planSubscription.getStatus());
            pstm.setInt(3, planSubscription.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(PlanSubscription planSubscription, Boolean hasId){
        DBConnection db = new DBConnection();
        String sql = """
            INSERT INTO PLAN_SUBSCRIPTION (id, start_date, cnpj_company, id_plan, number_installments, status)
            VALUES (?,?, ?, ?, ?, ?)
        """;

        if (hasId){
            try(Connection conn = db.connected();
                PreparedStatement psmt = conn.prepareStatement(sql);){
                psmt.setInt(1,planSubscription.getId());
                psmt.setDate(2, Date.valueOf(planSubscription.getStartDate()));
                psmt.setString(3, planSubscription.getCnpjCompany());
                psmt.setInt(4, planSubscription.getIdPlan());
                psmt.setInt(5, planSubscription.getNumberInstallments());
                psmt.setBoolean(6, planSubscription.getStatus());

                return psmt.executeUpdate() > 0;
            }catch (SQLException sqle) {
                System.out.println("[ERROR] Falied in insert" + sqle.getMessage());
                return false;
            }
        }

        sql = """
            INSERT INTO PLAN_SUBSCRIPTION (start_date, cnpj_company, id_plan, number_installments, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setDate(1, Date.valueOf(planSubscription.getStartDate()));
            psmt.setString(2, planSubscription.getCnpjCompany());
            psmt.setInt(3, planSubscription.getIdPlan());
            psmt.setInt(4, planSubscription.getNumberInstallments());
            psmt.setBoolean(5, planSubscription.getStatus());

            return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
            return false;
        }
    }
}
