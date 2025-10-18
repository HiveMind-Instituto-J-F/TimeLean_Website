package hivemind.hivemindweb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

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
            System.out.println("[ERROR] Falied in select" + e.getMessage());
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
            System.out.println("[ERROR] Falied in select" + e.getMessage());
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
            System.out.println("[ERROR] Falied in select" + e.getMessage());
        }
        return null;
    }

    public static List<Plant> selectFilter(String filter, FilterType filterType) throws IllegalArgumentException{
        DBConnection db = new DBConnection();
        List<Plant> plantList = new ArrayList<>();
        String sql;

        if (filterType == FilterType.INPUT_TEXT){
             sql = """
                    SELECT p.*
                    FROM PLANT p
                    JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                    WHERE c.name = ?
                    """;
        } else if (filterType == FilterType.INPUT_OPTION){
            if (filter.equals("active-plants")){
                sql = """
                    SELECT p.*
                    FROM PLANT p
                    JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                    WHERE c.IS_ACTIVE = TRUE AND p.OPERATIONAL_STATUS = TRUE;
                    """;
            } else if (filter.equals("inactive-plants")){
                sql = """
                        SELECT p.*
                        FROM PLANT p
                        JOIN COMPANY c ON c.cnpj = p.CNPJ_COMPANY
                        WHERE c.IS_ACTIVE = TRUE AND p.OPERATIONAL_STATUS  = FALSE;
                        """;
            } else if (filter.equals("all-plants")){
                sql = "SELECT * FROM PLANT";
            }
                else {
                    throw new IllegalArgumentException("Illegal Filter");
            }
        } else {
            throw new IllegalArgumentException("Illegal FilterType");
        }

        try (Connection conn = db.connected();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            if (filterType == FilterType.INPUT_TEXT){
                pstmt.setString(1, filter);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                plantList.add( new Plant(
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
            System.out.println("[ERROR] Falied in select" + e.getMessage());
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
                   address_number = ?
             WHERE cnpj = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, plant.getCNAE());
            pstm.setString(2, plant.getResponsibleCpf());
            pstm.setString(3, plant.getAdressCep());
            pstm.setInt(4, plant.getAdressNumber());
            pstm.setString(5, plant.getCNPJ()); // usado no WHERE
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static boolean switchOperationalStatus(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE plant
               SET OPERATIONAL_STATUS = ?
             WHERE cnpj = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setBoolean(1, plant.getOperationalStatus());
            pstm.setString(2, plant.getCNPJ());
            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Falied in update" + sqle.getMessage());
        }
        return false;
    }

    public static boolean delete(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM plant WHERE CNPJ = ?";

        try(Connection conn = db.connected()) { // Create Temp conn
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plant.getCNPJ());
            return pstmt.executeUpdate() >= 0;
        }catch (SQLException sqle){
            System.out.println("[ERROR] Falied in delete: " + sqle.getMessage());
        }
        return false;
    }

    public static boolean insert(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = "INSERT INTO plant (cnpj, cnae, responsible_cpf, operational_status, address_cep, address_number, cnpj_company) " +
                "VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, plant.getCNPJ());
            pstm.setString(2, plant.getCNAE());
            pstm.setString(3, plant.getResponsibleCpf());
            pstm.setBoolean(4, plant.getOperationalStatus());
            pstm.setString(5, plant.getAdressCep());
            pstm.setInt(6, plant.getAdressNumber());
            pstm.setString(7, plant.getCnpjCompany());

            return pstm.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.out.println("[ERROR] Failed in insert: " + sqle.getMessage());
        }
        return false;
    }

}
