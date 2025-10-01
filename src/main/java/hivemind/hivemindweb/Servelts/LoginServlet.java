package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import hivemind.hivemindweb.AuthService.AuthService;
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
            System.out.println("[WARN] Open LoginServelet");
            System.out.println("[WARN] Meth Use In Servelet: " + req.getMethod());
        
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if(email == null || email.isEmpty() || password == null || password.isEmpty()){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos ou nulos.");
                System.out.println("[ERROR] Invalid User");
                return;
            }

            Admin adminClient = new Admin(email, password);
            if(AuthService.login(adminClient)){
                req.getRequestDispatcher("//index.html").forward(req, resp);
                resp.setStatus(0);
                System.out.println("Login Sussefy");
            }
            else{
                System.out.println("[WARN] AdminLocal: email: "+ adminClient.getEmail() + "password: " + adminClient.getHashPassword());
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email ou senha incorretos.");
                req.setAttribute("errorMessage", "Email ou senha incorretos.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
            
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", se);
        }
        catch(NullPointerException npe){
            System.out.println("[ERROR] Null Pointer Exception: check for redundancy or incorrect memory allocation, Erro: " + npe.getMessage());
            req.setAttribute("error", npe);
        }

    }
}

