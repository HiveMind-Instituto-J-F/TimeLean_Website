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
    public static List<Plant> select() {
        List<Plant> plantsList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant";

        try (Connection conn = db.connected();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plant plantLocal = new Plant(
                        rs.getString("cnpj"),
                        rs.getString("cnae"),
                        rs.getString("responsible_cpf"),
                        rs.getBoolean("operational_status"),
                        rs.getString("address_cep"),
                        rs.getInt("address_number"),
                        rs.getString("cnpj_company")
                );
                plantsList.add(plantLocal);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select" + e.getMessage());
        }

        return plantsList;
    }

    public static List<Plant> select(String cnpj_company) {
        List<Plant> plantsList = new ArrayList<>();
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant WHERE CNPJ_COMPANY = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, cnpj_company);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Plant plantLocal = new Plant(
                        rs.getString("cnpj"),
                        rs.getString("cnae"),
                        rs.getString("responsible_cpf"),
                        rs.getBoolean("operational_status"),
                        rs.getString("address_cep"),
                        rs.getInt("address_number"),
                        rs.getString("cnpj_company")
                );
                plantsList.add(plantLocal);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select" + e.getMessage());
        }
        return plantsList;
    }

    public static Plant selectByPlantCnpj(String cnpj){
        DBConnection db = new DBConnection();
        String sql = "SELECT * FROM plant WHERE CNPJ = ?";

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, cnpj);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
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

        } catch (SQLException e) {
            System.err.println("[ERROR] Falied in select" + e.getMessage());
        }
        return null;
    }

    public static List<Plant> selectFilter(String filter, FilterType.Plant filterType) throws IllegalArgumentException {
        DBConnection db = new DBConnection();
        List<Plant> plantList = new ArrayList<>();
        String sql;

        switch (filterType) {
            case COMPANY_NAME -> {
                sql = """
                SELECT p.*
                FROM PLANT p
                JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                WHERE c.name = ?
                """;
            }
            case ACTIVE -> {
                sql = """
                SELECT p.*
                FROM PLANT p
                JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                WHERE p.OPERATIONAL_STATUS = TRUE
                """;
            }
            case INACTIVE -> {
                sql = """
                SELECT p.*
                FROM PLANT p
                JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                WHERE p.OPERATIONAL_STATUS = FALSE
                """;
            }
            case ALL_VALUES -> {
                sql = "SELECT * FROM PLANT";
            }
            default -> throw new IllegalArgumentException("Illegal FilterType");
        }

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filterType == FilterType.Plant.COMPANY_NAME) {
                pstmt.setString(1, filter);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                plantList.add(new Plant(
                        rs.getString("cnpj"),
                        rs.getString("cnae"),
                        rs.getString("responsible_cpf"),
                        rs.getBoolean("operational_status"),
                        rs.getString("address_cep"),
                        rs.getInt("address_number"),
                        rs.getString("cnpj_company")
                ));
            }

        } catch (SQLException e) {
            System.err.println("[FAILURE LOG] [" + LocalDateTime.now() + "] Failed in select: " + e.getMessage());
        }

        return plantList;
    }


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

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static boolean delete(String cnpj) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM plant WHERE CNPJ = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cnpj);
            return pstmt.executeUpdate() >= 0;
        }catch (SQLException sqle){
            System.err.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plant (cnpj, cnae, responsible_cpf, operational_status, address_cep, address_number, cnpj_company) " +
                "VALUES (?,?,?,?,?,?,?)";

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

        } catch (SQLException sqle) {
            System.err.println("[ERROR] Failed in insert: " + sqle.getMessage());
        }
        return false;
    }
}
