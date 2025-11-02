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
import hivemind.hivemindweb.models.Payment;

public class PaymentDAO {

    // [DATA ACCESS] Insert a new payment
    public static boolean insert(Payment payment) {
        DBConnection dbConnection = new DBConnection();
        String sql = """
            INSERT INTO payment (value, deadline, method, beneficiary, status, id_plan_subscription)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, payment.getValue());
            pstmt.setDate(2, java.sql.Date.valueOf(payment.getDeadline()));
            pstmt.setString(3, payment.getMethod());
            pstmt.setString(4, payment.getBeneficiary());
            pstmt.setString(5, payment.getStatus());
            pstmt.setInt(6, payment.getIdPlanSubscription());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO insert: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Update a payment
    public static boolean update(Payment payment) {
        DBConnection dbConnection = new DBConnection();
        String sql = """
            UPDATE payment
            SET deadline = ?, method = ?, status = ?
            WHERE id = ?
            """;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(payment.getDeadline()));
            pstmt.setString(2, payment.getMethod());
            pstmt.setString(3, payment.getStatus());
            pstmt.setInt(4, payment.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO update: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Delete a payment by ID
    public static boolean delete(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "DELETE FROM payment WHERE id = ?";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO delete: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Select all payments
    public static List<Payment> select() {
        List<Payment> paymentList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM payment ORDER BY id";

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                paymentList.add(mapResultSetToPayment(rs));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO select: " + sqle.getMessage());
        }

        return paymentList;
    }

    // [DATA ACCESS] Select payment by ID
    public static Payment select(int id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM payment WHERE id = ?";
        Payment paymentFromDb = null;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (id <= 0) throw new IllegalArgumentException("Valor Nulo: 'id'");

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    paymentFromDb = mapResultSetToPayment(rs);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO select: " + sqle.getMessage());
        }

        return paymentFromDb;
    }

    // [DATA ACCESS] Select pending payments for a company
    public static List<Payment> selectPendingPayments(String paramCompanyCnpj) {
        List<Payment> paymentList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql = """
            SELECT ps.id, c.cnpj, ps.number_installments, COUNT(p.id) AS actual_payments
            FROM plan_subscription ps
            LEFT JOIN payment p ON p.id_plan_subscription = ps.id
            LEFT JOIN company c ON c.cnpj = ps.cnpj_company
            WHERE c.cnpj = ?
            GROUP BY ps.id, c.cnpj, ps.number_installments
            HAVING COUNT(p.id) != ps.number_installments;
            """;

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paramCompanyCnpj);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    paymentList.add(mapResultSetToPayment(rs));
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO selectPendingPayments: " + sqle.getMessage());
        }

        return paymentList;
    }

    // [DATA ACCESS] Select payments with filter
    public static List<Payment> selectFilter(FilterType.Payment filterType, int filter) {
        List<Payment> paymentList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql;

        if (filterType == FilterType.Payment.ID_PLAN_SUBSCRIPTION) {
            sql = "SELECT * FROM payment WHERE id_plan_subscription = ?";
        } else if (filterType == FilterType.Payment.ALL_VALUES) {
            sql = "SELECT * FROM payment";
        } else {
            sql = "SELECT * FROM payment WHERE status = ?";
        }

        try (Connection conn = dbConnection.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filterType == FilterType.Payment.ID_PLAN_SUBSCRIPTION) {
                pstmt.setInt(1, filter);
            } else if (filterType == FilterType.Payment.PENDING) {
                pstmt.setString(1, "Pendente");
            } else if (filterType == FilterType.Payment.PAID) {
                pstmt.setString(1, "Pago");
            } else if (filterType == FilterType.Payment.CANCELED) {
                pstmt.setString(1, "Cancelado");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    paymentList.add(mapResultSetToPayment(rs));
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PaymentDAO selectFilter: " + sqle.getMessage());
        }

        return paymentList;
    }



    // [BUSINESS RULES] Map ResultSet to Payment object
    private static Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
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
}
