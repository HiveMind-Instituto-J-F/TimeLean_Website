package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Payment;

public class PaymentDAO {
    public static List<Payment> select(){
        List<Payment> PaymentList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM payment ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Payment PaymentLocal = new Payment(
                        rs.getInt("id"),
                        rs.getDouble("value"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getString("method"),
                        rs.getString("beneficiary"),
                        rs.getString("status"),
                        rs.getInt("id_plan_subscription")
                );
                PaymentList.add(PaymentLocal);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return PaymentList;
    }

    public static Payment select(int id){
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM payment WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getInt("id"),
                        rs.getDouble("value"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getString("method"),
                        rs.getString("beneficiary"),
                        rs.getString("status"),
                        rs.getInt("id_plan_subscription")
                );
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return null;
    }

    public static List<Payment> selectPendingPayments(String companyCnpj){
        List<Payment> paymentList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = """
                SELECT ps.id, c.cnpj, ps.number_installments, COUNT(p.id) AS actual_payments
                FROM plan_subscription ps
                LEFT JOIN payment p ON p.id_plan_subscription = ps.id
                LEFT JOIN company c ON c.cnpj = ps.cnpj_company
                WHERE c.cnpj = ?
                GROUP BY ps.id, c.cnpj, ps.number_installments
                HAVING COUNT(p.id) != ps.number_installments;


                """;

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, companyCnpj);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment PaymentLocal = new Payment(
                        rs.getInt("id"),
                        rs.getDouble("value"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getString("method"),
                        rs.getString("beneficiary"),
                        rs.getString("status"),
                        rs.getInt("id_plan_subscription")
                );
                paymentList.add(PaymentLocal);
            }
            return paymentList;
        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select: " + e.getMessage());
        }
        return null;
    }

    public static List<Payment> selectFilter(FilterType.Payment filterType, int filter){
        List<Payment> paymentList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = null;

        if (filterType == FilterType.Payment.ID_PLAN_SUBSCRIPTION){
            sql = """
                    SELECT *
                    FROM PAYMENT
                    WHERE id_plan_subscription = ?
                    """;
        } else if (filterType == FilterType.Payment.ALL_VALUES){
            sql = """
                    SELECT *
                    FROM PAYMENT
                    """;
        } else {
            sql = """
                    SELECT *
                    FROM PAYMENT
                    WHERE status = ?
                    """;
        }

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            if (filterType == FilterType.Payment.ID_PLAN_SUBSCRIPTION){
                pstmt.setInt(1, filter);
            } else if (!(filterType == FilterType.Payment.ALL_VALUES)){
                pstmt.setString(1, String.valueOf(filterType));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment PaymentLocal = new Payment(
                        rs.getInt("id"),
                        rs.getDouble("value"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getString("method"),
                        rs.getString("beneficiary"),
                        rs.getString("status"),
                        rs.getInt("id_plan_subscription")
                );
                paymentList.add(PaymentLocal);
            }
            return paymentList;
        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select: " + e.getMessage());
        }
        return null;
    }

    public static boolean delete(int id) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM payment WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Payment payment) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE payment
            SET deadline = ?,
                method = ?,
                status = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDate(1, java.sql.Date.valueOf(payment.getDeadline()));
            pstm.setString(2, payment.getMethod());
            pstm.setString(3, payment.getStatus());
            pstm.setInt(4, payment.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in update: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(Payment payment){
        DBConnection db = new DBConnection();
        String sql = """
            INSERT INTO payment (value,deadline,method,beneficiary,status,id_plan_subscription)
            VALUES (?,?,?,?,?,?)
        """;

        try(Connection conn = db.connected();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            psmt.setDouble(1, payment.getValue());
            psmt.setDate(2, java.sql.Date.valueOf(payment.getDeadline()));
            psmt.setString(3, payment.getMethod());
            psmt.setString(4, payment.getBeneficiary());
            psmt.setString(5, payment.getStatus());
            psmt.setInt(6, payment.getIdPlanSubscription());

            return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }
}
