package test;

import hivemind.hivemindweb.Connection.DBConnection;
//import hivemind.hivemindweb.models.Worker;
import hivemind.hivemindweb.Tool.*;
import java.sql.*;

public class TDDEnv {
    public static void main(String[] args) {
        try {
            System.out.println(Tool.hash("teste"));
        } catch (Exception e) {
            System.out.println("erro");
        }
        }
}

