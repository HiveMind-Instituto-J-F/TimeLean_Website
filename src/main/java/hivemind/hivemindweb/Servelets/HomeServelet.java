package hivemind.hivemindweb.Servelets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Nao era nessesario um /home porem caso o usuario queira abrir o site como /home sera possivel

@WebServlet("/home")
public class HomeServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }catch (ServletException se){
            se.printStackTrace();
        }
    }
}
