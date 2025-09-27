package hivemind.hivemindweb.Servelts;

import java.io.IOException;

import hivemind.hivemindweb.DAO.AdminDAO;
import hivemind.hivemindweb.models.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String email = req.getParameter("email");
        String password = req.getParameter("password");
            if(!(email.isEmpty() && email == null) || !(password.isEmpty() && password == null)){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos.");
                System.out.println("[ERROR] Invalid User");
            }

            Admin adminClient = new Admin(email, password);
            if(AdminDAO.login(adminClient)){
                req.getRequestDispatcher("\\index.html").forward(req,resp);
                return;
            }
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email ou senha incorretos.");

        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
        }
    }
}

