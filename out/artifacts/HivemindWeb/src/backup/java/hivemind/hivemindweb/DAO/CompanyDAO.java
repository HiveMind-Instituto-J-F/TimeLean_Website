package hivemind.hivemindweb.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.Services.Enums.FilterType;
import hivemind.hivemindweb.models.Company;

public class CompanyDAO {

    public static boolean insert(Company company) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO company (CNPJ, cnae, name, registrant_cpf) VALUES (?,?,?,?)";
        try (Connection conn = db.connected()) { // try-with-resources
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, company.getCNPJ());
            pstm.setString(2, company.getCnae());
            pstm.setString(3, company.getName());
            pstm.setString(4, company.getRegistrantCpf());
            return pstm.executeUpdate() > 0;
        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in insert: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean update(Company company) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE company SET name = ?, cnae = ?, registrant_cpf = ? WHERE cnpj = ?";
        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, company.getName());
            pstm.setString(2, company.getCnae());
            pstm.setString(3, company.getRegistrantCpf());
            pstm.setString(4, company.getCNPJ());
            return pstm.executeUpdate() > 0;
        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static List<Company> selectFilter(FilterType.Company filter) {
        List<Company> companysList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql;

        if (FilterType.Company.ACTIVE == filter) {
            sql = "SELECT * FROM company WHERE IS_ACTIVE = TRUE ORDER BY CNPJ";
        } else if (FilterType.Company.INACTIVE == filter) {
            sql = "SELECT * FROM company WHERE IS_ACTIVE = FALSE ORDER BY CNPJ";
        } else if (FilterType.Company.WITH_PENDING_PAYMENT == filter) {
            sql = """
                    SELECT DISTINCT c.*
                    FROM COMPANY c
                    JOIN PLAN_SUBSCRIPTION ps ON c.CNPJ = ps.CNPJ_COMPANY
                    JOIN PAYMENT p ON ps.ID = p.ID_PLAN_SUBSCRIPTION
                    WHERE p.STATUS = 'PENDING'
                    """;
        } else {
            sql = "SELECT * FROM company ORDER BY CNPJ";
        }

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Company companyLocal = new Company(
                        rs.getString("CNPJ"),
                        rs.getString("name"),
                        rs.getString("cnae"),
                        rs.getString("registrant_cpf"),
                        rs.getBoolean("is_active")
                );
                companysList.add(companyLocal);
            }
        } catch (SQLException sqle) {
            System.err.println("[ERROR] Failed in select: " + sqle.getMessage());
        }

        System.out.println("[DEBUG] In select CompanyDAO, Companies found: " + companysList.size());
        return companysList;
    }

    public static Company select(String cnpj) {
        // Variable to store the result of the query
        Company companyLocal = null;
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM company WHERE CNPJ = ? ORDER BY CNPJ";

        // Connect to the database and prepare the statement
        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            // Set the CNPJ parameter in the query
            pstm.setString(1, cnpj);

            // Execute the query and get the result set
            try (ResultSet rs = pstm.executeQuery()) {
                // If a company is found, create a Company object
                if (rs.next()) {
                    companyLocal = new Company(
                            //Wait for DB colums is create
                            rs.getString("CNPJ"),
                            rs.getString("name"),
                            rs.getString("cnae"),
                            rs.getString("registrant_cpf"),
                            rs.getBoolean("is_active")
                    );
                }
            }
            // Handle SQL exceptions
        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in select: " + sqle.getMessage());
        }

        // Debug log with the found company
        System.out.println("[DEBUG] In select EmrpesaDAO ,Company found: " + companyLocal);

        // Return the found company (or null if not found)
        return companyLocal;
    }
}
