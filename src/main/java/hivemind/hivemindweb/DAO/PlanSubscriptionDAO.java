package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.PlanSubscription;

public class PlanSubscriptionDAO {

    // [DATA ACCESS] Select all plan subscriptions
    public static List<PlanSubscription> select() {
        List<PlanSubscription> planSubscriptionList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan_subscription ORDER BY id";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                planSubscriptionList.add(mapResultSetToPlanSubscription(rs));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO select: " + sqle.getMessage());
        }

        return planSubscriptionList;
    }

    // [DATA ACCESS] Select a plan subscription by ID
    public static PlanSubscription select(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan_subscription WHERE id = ?";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlanSubscription(rs);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO selectById: " + sqle.getMessage());
        }

        return null;
    }

    // [DATA ACCESS] Select plan subscriptions by filter
    public static List<PlanSubscription> selectFilter(FilterType.PlanSubscription filterType, String filter) {
        List<PlanSubscription> planSubscriptionList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
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

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filterType == FilterType.PlanSubscription.ID_PLAN) {
                pstmt.setInt(1, Integer.parseInt(filter));
            } else if (filterType == FilterType.PlanSubscription.CNPJ_COMPANY) {
                pstmt.setString(1, filter);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    planSubscriptionList.add(mapResultSetToPlanSubscription(rs));
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO selectFilter: " + sqle.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO invalid number in filter: " + filter);
        }

        return planSubscriptionList;
    }

    // [DATA ACCESS] Select active plans for a company
    public static List<PlanSubscription> selectActivePlans(String paramCnpjCompany) {
        List<PlanSubscription> planSubscriptionList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM plan_subscription WHERE cnpj_company = ? AND status = true";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paramCnpjCompany);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    planSubscriptionList.add(mapResultSetToPlanSubscription(rs));
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO selectActivePlans: " + sqle.getMessage());
        }

        return planSubscriptionList;
    }

    // [DATA ACCESS] Update a plan subscription
    public static boolean update(PlanSubscription planSubscription) {
        DBConnection dbConnection = new DBConnection();
        String sql = """
            UPDATE plan_subscription
            SET start_date = ?,
                status = ?
            WHERE id = ?
        """;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(planSubscription.getStartDate()));
            pstmt.setBoolean(2, planSubscription.getStatus());
            pstmt.setInt(3, planSubscription.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO update: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Insert a plan subscription
    public static boolean insert(PlanSubscription planSubscription, boolean hasId) {
        DBConnection dbConnection = new DBConnection();
        String sqlWithId = """
            INSERT INTO plan_subscription (id, start_date, cnpj_company, id_plan, number_installments, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        String sqlWithoutId = """
            INSERT INTO plan_subscription (start_date, cnpj_company, id_plan, number_installments, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(hasId ? sqlWithId : sqlWithoutId)) {

            if (hasId) {
                pstmt.setInt(1, planSubscription.getId());
                pstmt.setDate(2, Date.valueOf(planSubscription.getStartDate()));
                pstmt.setString(3, planSubscription.getCnpjCompany());
                pstmt.setInt(4, planSubscription.getIdPlan());
                pstmt.setInt(5, planSubscription.getNumberInstallments());
                pstmt.setBoolean(6, planSubscription.getStatus());
            } else {
                pstmt.setDate(1, Date.valueOf(planSubscription.getStartDate()));
                pstmt.setString(2, planSubscription.getCnpjCompany());
                pstmt.setInt(3, planSubscription.getIdPlan());
                pstmt.setInt(4, planSubscription.getNumberInstallments());
                pstmt.setBoolean(5, planSubscription.getStatus());
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlanSubscriptionDAO insert: " + sqle.getMessage());
        }

        return false;
    }

    // [BUSINESS RULES] Map ResultSet to PlanSubscription object
    private static PlanSubscription mapResultSetToPlanSubscription(ResultSet rs) throws SQLException {
        return new PlanSubscription(
                rs.getInt("id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getString("cnpj_company"),
                rs.getInt("id_plan"),
                rs.getInt("number_installments"),
                rs.getBoolean("status")
        );
    }
}
