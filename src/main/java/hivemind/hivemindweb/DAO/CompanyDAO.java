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
import hivemind.hivemindweb.models.Company;

public class CompanyDAO {

    // [DATA ACCESS] Insert a new company
    public static boolean insert(Company company) {
        DBConnection dbConnection = new DBConnection();
        String sql = "INSERT INTO company (cnpj, cnae, name, registrant_cpf) VALUES (?,?,?,?)";

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, company.getCnpj());
            preparedStatement.setString(2, company.getCnae());
            preparedStatement.setString(3, company.getName());
            preparedStatement.setString(4, company.getRegistrantCpf());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] CompanyDAO insert: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Update a company
    public static boolean update(Company company) {
        DBConnection dbConnection = new DBConnection();
        String sql = "UPDATE company SET name = ?, cnae = ?, registrant_cpf = ?, is_active = ? WHERE cnpj = ?";

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getCnae());
            preparedStatement.setString(3, company.getRegistrantCpf());
            preparedStatement.setBoolean(4, company.isActive());
            preparedStatement.setString(5, company.getCnpj());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] CompanyDAO update: " + sqle.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Select companies by filter
    public static List<Company> selectFilter(FilterType.Company filter) {
        List<Company> companiesList = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String sql;

        if (filter == FilterType.Company.ACTIVE) {
            sql = "SELECT * FROM company WHERE is_active = TRUE ORDER BY cnpj";
        } else if (filter == FilterType.Company.INACTIVE) {
            sql = "SELECT * FROM company WHERE is_active = FALSE ORDER BY cnpj";
        } else if (filter == FilterType.Company.WITH_PENDING_PAYMENT) {
            sql = """
                SELECT DISTINCT c.*
                FROM company c
                JOIN plan_subscription ps ON c.cnpj = ps.cnpj_company
                JOIN payment p ON ps.id = p.id_plan_subscription
                WHERE p.status = 'PENDING'
                """;
        } else {
            sql = "SELECT * FROM company ORDER BY cnpj";
        }

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                companiesList.add(mapResultSetToCompany(resultSet));
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] CompanyDAO selectFilter: " + sqle.getMessage());
        }

        return companiesList;
    }

    // [DATA ACCESS] Select a company by CNPJ
    public static Company select(String cnpj) {
        DBConnection dbConnection = new DBConnection();
        Company companyFromDb = null;
        String sql = "SELECT * FROM company WHERE cnpj = ? ORDER BY cnpj";

        try (Connection connection = dbConnection.connected();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (cnpj == null || cnpj.isEmpty()) throw new IllegalArgumentException("Valor Nulo: 'cnpj'");

            preparedStatement.setString(1, cnpj);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    companyFromDb = mapResultSetToCompany(resultSet);
                }
            }

        } catch (SQLException sqle) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] CompanyDAO select: " + sqle.getMessage());
        }

        return companyFromDb;
    }

    // [BUSINESS RULES] Map ResultSet to Company object
    private static Company mapResultSetToCompany(ResultSet rs) throws SQLException {
        return new Company(
                rs.getString("cnpj"),
                rs.getString("name"),
                rs.getString("cnae"),
                rs.getString("registrant_cpf"),
                rs.getBoolean("is_active")
        );
    }
}
