package test;

import hivemind.hivemindweb.Connection.DBConnection;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.Tool.Tool;

import java.sql.*;

public class TDDEnv {
    public static void main(String[] args) {
        System.out.println(Tool.verifySQL("truncate"));
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

            if(WorkerDAO.insert("1234' OR '1'='1", "João", "Silva", "TI", "Dev", 1)){
                System.out.println("Insert successful (TEst falhou)");
            }
            else {
                System.out.println("Insert failed (Test aprovado)");
            }


            selectTDD(db);
            WorkerDAO.update("tipo_perfil","TESTDEV",999);
            selectTDD(db);
            if(WorkerDAO.insert("5849235095","name","riepje","setor","role",798432)){
                System.out.println("Isnert Beem Sussesdido!");
            }
            else {
                System.out.println("Erro ao inserir");
            }
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

            // --- Select trabalhador ---
            String sqlTrab = "SELECT * FROM trabalhador"; // ambiente de teste
            try (PreparedStatement pstmtTrab = conn.prepareStatement(sqlTrab);
                 ResultSet rsTrab = pstmtTrab.executeQuery()) {

                System.out.println("\n=== Trabalhadores ===");
                while (rsTrab.next()) {
                    System.out.printf(
                            "| ID: %-3d | Planta: %-3d | Perfil: %-10s | Login: %-15s | Senha: %-12s | Setor: %-12s |%n",
                            rsTrab.getInt("id"),
                            rsTrab.getInt("id_planta"),
                            rsTrab.getString("tipo_perfil"),
                            rsTrab.getString("login"),
                            rsTrab.getString("senha"),
                            rsTrab.getString("setor")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

