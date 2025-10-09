package hivemind.hivemindweb.Servelts.Worker;//package hivemind.hivemindweb.Servelts.Worker;
import hivemind.hivemindweb.DAO.PlantDAO;
import hivemind.hivemindweb.DAO.WorkerDAO;
import hivemind.hivemindweb.models.Plant;
import hivemind.hivemindweb.models.Worker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/read")
public class Read extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String plantCnpj = (String) session.getAttribute("plantCnpj");
        Plant plant = PlantDAO.selectByPlantCnpj(plantCnpj);
        List<Worker> workers = WorkerDAO.selectByPlantCnpj(plant.getCNPJ());
        System.out.println("DEBUGGGG");
        System.out.println(workers);
        request.setAttribute("workers", workers);
        request.setAttribute("plantCnpj", plantCnpj);
        request.getRequestDispatcher("html/crud/worker/read.jsp").forward(request, response);
    }

}