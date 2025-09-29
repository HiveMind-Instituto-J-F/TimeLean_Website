package hivemind.hivemindweb.Servelts;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if(email == null || email.isEmpty() || password == null || password.isEmpty()){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email ou senha inválidos ou nulos.");
                System.out.println("[ERROR] Invalid User");
                return;
            }

            // Verify email according to regex:
            if (!validateEmailByRegex(email)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de email não suportado");
                System.out.println("[ERROR] Unsupported email");
                return;
            }

            Admin adminClient = new Admin(email, password);
            if(AuthService.login(adminClient)){
                req.getRequestDispatcher("home").forward(req,resp);
                System.out.println("Login Sussefy");
            }
            else{
                System.out.println("[WARN] AdminLocal: email: "+ adminClient.getEmail() + "password: " + adminClient.getHashPassword());
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email ou senha incorretos.");
            }
            
        }catch(ServletException se){
            System.out.println("[ERROR] Error In Login, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
        }
        catch(NullPointerException npe){
            System.out.println("[ERROR] Null Pointer Exception: check for redundancy or incorrect memory allocation, Erro: " + npe.getMessage());
        }

    }
    public static boolean validateEmailByRegex(String email){
        // Create regex:
        Pattern pattern = Pattern.compile("^[A-Za-z]+[\\.\\-_][A-Za-z]+@(germinare\\.org\\.br|hivemind\\.com\\.br)$");
        Matcher matcher = pattern.matcher(email.trim());

        // Verify if email is following rules
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}

