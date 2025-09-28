package test;

import java.util.List;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.Tool.Tool;
import hivemind.hivemindweb.models.Admin;

public class TDDEnv {
    public static void main(String[] args) {
        try {
            System.out.println(Tool.hash("TESTE"));
        } catch (Exception e) {
            System.out.println("erro");
        }

        // String password = "teste";
        // String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        // System.out.println(hash);

        List<Admin> t = AdminDAO.select();

        for(Admin i : t){
            System.out.println(i);
        }
    }
}

