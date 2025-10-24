package hivemind.hivemindweb.Servelts.crud.Company;

import hivemind.hivemindweb.DAO.CompanyDAO;
import hivemind.hivemindweb.models.Company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/company/read")
public class Read extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try{
            List<Company> companies;
            String status = req.getParameter("status");

            // Validate initial status
            try{
                if(status.isEmpty()) status = null;
            } catch (NullPointerException npe){
                status = null;
            }
            
            String filter = "active-companies"; // Default filter
            if (status != null) {
                if (status.equalsIgnoreCase("active-companies")) {
                    filter = "active-companies";
                } else if (status.equalsIgnoreCase("inactive-companies")) {
                    filter = "inactive-companies";
                } else if (status.equalsIgnoreCase("companies-with-pending-payments")){
                    filter = "companies-with-pending-payments";
                } else if (status.equalsIgnoreCase("all-companies")){
                    filter = "all-companies";
                }
                else {
                    System.err.println("[WARN] Invalid filter.");
                    req.setAttribute("errorMessage", "Invalid filter.");
                    req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
                    return;
                }
            }
            
            companies = CompanyDAO.selectFilter(filter);
            
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/html/crud/company/read.jsp").forward(req, resp);
        }catch(IllegalArgumentException ia){
            System.out.println("[ERROR] Error In Create Servelet, Error: "+ ia.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + ia.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + ia.getMessage());
            req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
        }
        catch(ServletException se){
            System.out.println("[ERROR] Error In Servelet Dispacher, Error: "+ se.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[ERROR] Ocorreu um erro interno no servidor. " + req.getMethod() + "Erro: " + se.getMessage());
            req.setAttribute("errorMessage", "[ERROR] Ocorreu um erro interno no servidor: " + se.getMessage());
            req.getRequestDispatcher("\\html\\error\\error.jsp").forward(req, resp);
        }
        }
    }
    
/*
* BUSINESS RULES (DO NOT DELETE):
* If the company is set as deactivated, it will be treated the same as a deleted company; this means, it cannot make any operation.
* Deleted/deactivated companies may be visible depending on the used filter.
* */