package hivemind.hivemindweb.Servelts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServerlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String user = req.getParameter("ussername");
            String password = req.getParameter("passoword");
            if(!(user.isEmpty() && user == null) || !(password.isEmpty() && password == null)){
                System.out.println("[ERROR] Invalid User");
            }
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
        }
    }
}

