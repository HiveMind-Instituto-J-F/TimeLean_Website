package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Admin;

public class AdminDAO {

    // [DATA ACCESS] Retrieve all admins
    public static List<Admin> select() {
        DBConnection dbConnection = new DBConnection();
        List<Admin> adminsList = new ArrayList<>();
        String sql = "SELECT id, email, password FROM admin ORDER BY id";

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                adminsList.add(mapResultSetToAdmin(resultSet));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] AdminDAO select: " + sqle.getMessage());
        }

        return adminsList;
    }

    // [DATA ACCESS] Retrieve an admin by email
    public static Admin selectByEmail(String email) {
        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT id, email, password FROM admin WHERE email = ?";

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Null value: 'email'");

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAdmin(resultSet);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] AdminDAO selectByEmail: " + sqle.getMessage());
        }

        return null;
    }

    // [BUSINESS RULES] Map ResultSet to Admin object
    private static Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
