package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import javax.security.auth.login.LoginException;

import hivemind.hivemindweb.AuthService.AuthService;
import hivemind.hivemindweb.models.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            System.out.println("[WARN] Open LoginServelet");
            System.out.println("[WARN] Meth Use In Servelet: " + req.getMethod());
            
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            
            if(email.isEmpty() || password == null || password.isEmpty()){
                throw new IllegalArgumentException("Email ou senha incorretos");
            }
            
            Admin adminClient = new Admin(email, password);
            //Create A HTTP Session
            HttpSession session = req.getSession(true);
            session.setMaxInactiveInterval(600);

            if(AuthService.login(adminClient)){
                req.getRequestDispatcher("//html//crud//create_company.jsp").forward(req, resp);
                resp.setStatus(200);
                session.setAttribute("user", adminClient);
                session.setAttribute("login", true);
                System.out.println("Login Sussefy");
            }
            else{
                session.setAttribute("login", false);
                throw new LoginException("Email ou senha incorretos.");
            }
            
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("error", se.getMessage());
        }
        catch(NullPointerException npe){
            System.out.println("[ERROR] Null Pointer Excepti    on: check for redundancy or incorrect memory allocation, Erro: " + npe.getMessage());
            req.setAttribute("error", npe.getMessage());
        }
        catch(IllegalArgumentException ime){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos ou nulos.");
            System.out.println("[ERROR] Invalid User");
            System.out.println("[ERROR] Input Exception: check for redundancy or incorrect memory allocation, Erro: " + ime.getMessage());
            req.setAttribute("error", ime.getMessage());
            req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
        }
        catch(LoginException le){
            req.setAttribute("errorMessage", le.getMessage());
            req.getRequestDispatcher("html/login.jsp").forward(req, resp);
        }

    }
}   

