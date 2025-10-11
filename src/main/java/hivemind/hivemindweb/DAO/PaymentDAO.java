package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Payment;

public class PaymentDAO {
    public static List<Payment> select(){
        List<Payment> PaymentList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM payment ORDER BY id";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            System.out.println("[ERROR] Falied in select: " + e.getMessage());
        }

        return PaymentList;
    }

    public static boolean delete(Payment payment) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM payment WHERE id = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, payment.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Payment payment) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE payment
            SET value = ?,
                deadline = ?,
                method = ?,
                beneficiary = ?,
                status = ?,
                id_plan_subscription = ?
            WHERE id = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDouble(1, payment.getValue());
            pstm.setDate(2, java.sql.Date.valueOf(payment.getDeadline()));
            pstm.setString(3, payment.getMethod());
            pstm.setString(4, payment.getBeneficiary());
            pstm.setString(5, payment.getStatus());
            pstm.setInt(6, payment.getIdPlan());
            pstm.setInt(7, payment.getId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update: " + sqle.getMessage());
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
            psmt.setInt(6, payment.getIdPlan());
            psmt.setInt(7, payment.getNumberInstallments());
            return psmt.executeUpdate() > 0;
        }catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }
}
