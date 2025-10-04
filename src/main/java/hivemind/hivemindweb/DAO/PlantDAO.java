package hivemind.hivemindweb.DAO;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.models.Plant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        List<Plant> plantsList = new ArrayList<>();
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

    public static boolean update(Plant plant) {
        DBConnection db = new DBConnection();
        String sql = """
            UPDATE plant
               SET cnae = ?,
                   responsible_cpf = ?,
                   operational_status = ?,
                   address_cep = ?,
                   address_number = ?
             WHERE cnpj = ?
        """;

        try (Connection conn = db.connected();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, plant.getCNAE());
            pstm.setString(2, plant.getResponsibleCpf());
            pstm.setBoolean(3, plant.getOperationalStatus());
            pstm.setString(4, plant.getAdressCep());
            pstm.setInt(5, plant.getAdressNumber());
            pstm.setString(6, plant.getCNPJ()); // usado no WHERE
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
