package hivemind.hivemindweb.Servelets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contact")
public class ContactServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }catch (ServletException se){
            se.printStackTrace();
        }
    }
}
