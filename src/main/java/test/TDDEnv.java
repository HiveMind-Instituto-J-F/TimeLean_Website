package test;

import java.util.List;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;

public class TDDEnv {
    public static void main(String[] args) {
        try {
            System.out.println(AuthService.hash("teste"));
        } catch (Exception e) {
            System.out.println("erro");
        }
        
        List<Admin> t = AdminDAO.select();

        for(Admin i : t){
            System.out.println(i);
        }
    }
}

