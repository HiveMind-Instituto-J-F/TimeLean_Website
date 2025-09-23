package test;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.DAO.WorkerDAO;
//import hivemind.hivemindweb.models.Worker;

import java.sql.*;
import java.util.List;

public class TDDEnv {
    public static void main(String[] args) {
        Connection conn = null;
        DBConnection db = new DBConnection();
        try {
            selectTDD(db);
//            PreparedStatement pstmt2 = conn.prepareStatement(
//                    "INSERT INTO empresa(id, nome, cnpj, time_empresa) VALUES (?,?, ?, ?)"
//            );
//            pstmt2.setInt(1, 432);
//            pstmt2.setString(2, "Hivemind_Test2");
//            pstmt2.setString(3, "3948230");
//            pstmt2.setString(4, "Backend");
//            pstmt2.executeUpdate();
//
//            selectTDD(db);
//            WorkerDAO.update("tipo_perfil","TESTDEV15",999);
//            List<Worker> data = WorkerDAO.select();
//            System.out.println(data);
//            for (Worker worker : data) {
//                System.out.println("Worker: " + worker);
//            }

//            List<Company> dataEmpresa = EmpresaDAO.select();
//            for (Company company : dataEmpresa) {
//                System.out.println("Company: " + company);
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectTDD(DBConnection db) {
        System.out.println("______________________________selectTDD______________________________");

        try (Connection conn = db.connected()) {

            // --- Select empresa ---
            String sqlEmpresa = "SELECT * FROM empresa"; // ambiente de teste
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEmpresa);
                 ResultSet rs = pstmt.executeQuery()) {

                System.out.println("=== Empresa ===");
                while (rs.next()) {
                    System.out.printf("Nome: %-20s | CNPJ: %-15s | Time: %-10s%n",
                            rs.getString("nome"),
                            rs.getString("cnpj"),
                            rs.getString("time_empresa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

