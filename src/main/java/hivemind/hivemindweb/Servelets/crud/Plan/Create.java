package hivemind.hivemindweb.Servelets.crud.Plan;

import java.io.IOException;

import hivemind.hivemindweb.DAO.PlanDAO;
import hivemind.hivemindweb.Exception.InvalidForeignKeyException;
import hivemind.hivemindweb.models.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plan/create")
public class Create extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IOException, ServletException {
        try{
            String durationStr = req.getParameter("duration");
            if(durationStr == null || durationStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'duration'");}
            int duration = Integer.parseInt(durationStr);

            String description = req.getParameter("description");
            if(description == null || description.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'description'");}

            String priceStr = req.getParameter("price");
            if(priceStr == null || priceStr.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'price'");}
            double price = Double.parseDouble(priceStr);

            String name = req.getParameter("name");
            if(name.isEmpty()){throw new IllegalArgumentException("Values Is Null, Value: 'nameStr'");}
            
            String nameDB = PlanDAO.getName(name);
    
            if(nameDB.equalsIgnoreCase(name)){
                throw new InvalidForeignKeyException("Name already exists in the database");
            }
            
            Plan planLocal = new Plan(name, description, duration, price);
            System.out.println(planLocal);
            if(PlanDAO.insert(planLocal,false)){
                System.out.println("[INFO] Insert Plan Sussefly");
                req.setAttribute("msg", "Plan Foi Adicionado Com Susseso!");
                req.getRequestDispatcher("html\\crud\\plan.jsp").forward(req, resp);
            }
            else{
                System.out.println("[ERROR] Erro in PlanDAO");
                System.out.println("[WARN] Plan Not Added Due to Error!");
                System.out.println("[ERROR]:" + nameDB + " " +  planLocal);
                req.setAttribute("Errro", "Plan Nao foi Adicionado devido a um Erro!");
            }

            String nameParam = req.getParameter("name");
            if (nameParam == null || nameParam.isEmpty()) {
                throw new IllegalArgumentException("Values Is Null, Value: 'name'");
            }

            // [LOGIC] Create local Plan object and insert into database
            Plan planLocal = new Plan(nameParam,price,duration);
            planLocal.setActive(true);

            if (PlanDAO.insert(planLocal, false)) {
                // [SUCCESS LOG] Log successful plan creation
                System.err.println("[SUCCESS LOG] Plan successfully created: " + nameParam);
                req.setAttribute("msg", "Plano adicionado com sucesso!");
                resp.sendRedirect(req.getContextPath() + "/plan/read");
            } else {
                // [ERROR] Log failure when inserting plan
                System.err.println("[ERROR] Failed to insert plan into database.");
                req.setAttribute("errorMessage", "O plano não foi adicionado devido a um erro interno.");
                req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
                req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
            }

        } catch (IllegalArgumentException ia) {
            // [ERROR] Handle invalid argument exception
            System.err.println("[ERROR] IllegalArgumentException occurred: " + ia.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro nos dados fornecidos: " + ia.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (NullPointerException npe) {
            // [ERROR] Handle null pointer exception
            System.err.println("[ERROR] NullPointerException occurred: " + npe.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro ao processar os dados. Verifique os campos e tente novamente.");
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (ServletException se) {
            // [ERROR] Handle servlet exception
            System.err.println("[ERROR] ServletException occurred: " + se.getMessage());
            req.setAttribute("errorMessage", "Erro interno ao redirecionar a página: " + se.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (IOException ioe) {
            // [ERROR] Handle IO exception
            System.err.println("[ERROR] IOException occurred: " + ioe.getMessage());
            req.setAttribute("errorMessage", "Erro de entrada/saída ao processar a solicitação: " + ioe.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);

        } catch (Exception e) {
            // [ERROR] Handle unexpected exceptions
            System.err.println("[ERROR] Unexpected exception occurred: " + e.getMessage());
            req.setAttribute("errorMessage", "Ocorreu um erro inesperado: " + e.getMessage());
            req.setAttribute("errorUrl", "/html/crud/plan/create.jsp");
            req.getRequestDispatcher("/html/error/error.jsp").forward(req, resp);
        }
    }
}
