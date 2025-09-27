package test;

import java.util.List;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.Tool.Tool;
import hivemind.hivemindweb.models.Admin;

public class TDDEnv {
    public static void main(String[] args) {
        try {
            System.out.println(Tool.hash("t"));
        } catch (Exception e) {
            System.out.println("erro");
        }

        List<Admin> t = AdminDAO.select();

        for(Admin i : t){
            System.out.println(i);
        }
    }
}

