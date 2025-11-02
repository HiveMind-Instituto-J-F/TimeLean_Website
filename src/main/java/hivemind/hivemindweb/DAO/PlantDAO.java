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
import hivemind.hivemindweb.models.Plant;

public class PlantDAO {

    // [DATA ACCESS] Select all plants
    public static List<Plant> select() {
        List<Plant> plantsList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plant plantFromDb = mapResultSetToPlant(rs);
                plantsList.add(plantFromDb);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO select: " + e.getMessage());
        }

        return plantsList;
    }

    // [DATA ACCESS] Select plants by company CNPJ
    public static List<Plant> select(String cnpjCompany) {
        List<Plant> plantsList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant WHERE CNPJ_COMPANY = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cnpjCompany);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Plant plantFromDb = mapResultSetToPlant(rs);
                plantsList.add(plantFromDb);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO select by company: " + e.getMessage());
        }

        return plantsList;
    }

    // [DATA ACCESS] Select single plant by CNPJ
    public static Plant selectByPlantCnpj(String cnpj) {
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant WHERE CNPJ = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cnpj);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPlant(rs);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO select by CNPJ: " + e.getMessage());
        }

        return null;
    }

    // [DATA ACCESS] Select plants with filter
    public static List<Plant> selectFilter(String filter, FilterType.Plant filterType) throws IllegalArgumentException {
        if (filterType == null) throw new IllegalArgumentException("Valor Nulo: 'filterType'");

        DBConnection db = new DBConnection();
        List<Plant> plantList = new ArrayList<>();
        String sql;

        switch (filterType) {
            case COMPANY_NAME -> sql = """
                    SELECT p.*
                    FROM PLANT p
                    JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                    WHERE c.name = ?
                    """;
            case ACTIVE -> sql = """
                    SELECT p.*
                    FROM PLANT p
                    JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                    WHERE p.OPERATIONAL_STATUS = TRUE
                    """;
            case INACTIVE -> sql = """
                    SELECT p.*
                    FROM PLANT p
                    JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                    WHERE p.OPERATIONAL_STATUS = FALSE
                    """;
            case ALL_VALUES -> sql = "SELECT * FROM PLANT";
            default -> throw new IllegalArgumentException("Tipo de filtro ilegal");
        }

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filterType == FilterType.Plant.COMPANY_NAME) {
                pstmt.setString(1, filter);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                plantList.add(mapResultSetToPlant(rs));
            }

        } catch (SQLException e) {
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] PlantDAO selectFilter: " + e.getMessage());
        }

        return plantList;
    }

    // [DATA ACCESS] Insert new plant
    public static boolean insert(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plant (cnpj, cnae, responsible_cpf, operational_status, address_cep, address_number, cnpj_company) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, plant.getCnpj());
            pstm.setString(2, plant.getCnae());
            pstm.setString(3, plant.getResponsibleCpf());
            pstm.setBoolean(4, plant.getOperationalStatus());
            pstm.setString(5, plant.getAddressCep());
            pstm.setInt(6, plant.getAddressNumber());
            pstm.setString(7, plant.getCnpjCompany());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO insert: " + e.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Update plant
    public static boolean update(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = """
                UPDATE plant
                   SET cnae = ?,
                       responsible_cpf = ?,
                       address_cep = ?,
                       address_number = ?,
                       OPERATIONAL_STATUS = ?
                 WHERE cnpj = ?
                """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, plant.getCnae());
            pstm.setString(2, plant.getResponsibleCpf());
            pstm.setString(3, plant.getAddressCep());
            pstm.setInt(4, plant.getAddressNumber());
            pstm.setBoolean(5, plant.getOperationalStatus());
            pstm.setString(6, plant.getCnpj());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO update: " + e.getMessage());
        }

        return false;
    }

    // [DATA ACCESS] Delete plant
    public static boolean delete(String cnpj) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM plant WHERE CNPJ = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cnpj);
            return pstmt.executeUpdate() >= 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] PlantDAO delete: " + e.getMessage());
        }

        return false;
    }

    // [BUSINESS RULES] Map ResultSet to Plant object
    private static Plant mapResultSetToPlant(ResultSet rs) throws SQLException {
        return new Plant(
                rs.getString("cnpj"),
                rs.getString("cnae"),
                rs.getString("responsible_cpf"),
                rs.getBoolean("operational_status"),
                rs.getString("address_cep"),
                rs.getInt("address_number"),
                rs.getString("cnpj_company")
        );
    }
}
