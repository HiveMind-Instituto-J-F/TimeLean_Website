package hivemind.hivemindweb.Servelts.Email;

import java.io.IOException;
import java.time.LocalDateTime;

import hivemind.hivemindweb.Services.Email.EmailService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/email/send")
public class Send extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // [VALIDATION] Validate required input parameters
            String paramSector = request.getParameter("sector");
            if (paramSector == null || paramSector.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'sector'");
            }

            String paramSender = request.getParameter("sender");
            if (paramSender == null || paramSender.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'sender'");
            }

            String paramSubject = request.getParameter("subject");
            if (paramSubject == null || paramSubject.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'subject'");
            }

            String paramUserMessage = request.getParameter("msg");
            if (paramUserMessage == null || paramUserMessage.isEmpty()) {
                throw new IllegalArgumentException("Valor Nulo: 'msg'");
            }

            // [PROCESS] Construct email message content
            String emailContent = """
                                    Olá,
                                
                                    Você recebeu um novo contato de um cliente da HiveMind referente à aplicação TimeLean.
                                    Setor escolhido pelo usuário: %s
                                    Cliente/Remetente: %s
                                
                                    Mensagem do cliente:
                                    %s
                                
                                    Observação: Este e-mail foi enviado pelo sistema da HiveMind e será direcionado à equipe responsável.
                                
                                    Atenciosamente,
                                    Equipe HiveMind
                                    """.formatted(paramSector, paramSender, paramUserMessage);


            // [DATA ACCESS] Retrieve EmailService from servlet context
            ServletContext context = request.getServletContext();
            EmailService emailServiceFromDb = (EmailService) context.getAttribute("EmailService");

            // [BUSINESS RULES] Attempt to send email
            boolean emailSent = emailServiceFromDb.sendEmail(paramSubject, emailContent);

            if (emailSent) {
                // [SUCCESS LOG] Log successful email sending
                System.out.println("[INFO] [" + LocalDateTime.now() + "] Email sent successfully");
                request.setAttribute("msg", "E-mail enviado com sucesso!");
            } else {
                // [FAILURE LOG] Log failure to send email
                System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Send] EmailService: Failed to send email");
                request.setAttribute("msg", "Houve algum erro inesperado.");
            }

            // [PROCESS] Forward to confirmation page
            request.getRequestDispatcher("/pages/email/send.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            // [FAILURE LOG] Log validation error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Send] IllegalArgumentException: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro: " + e.getMessage());
            request.setAttribute("errorUrl", "/pages/email/send.jsp");
            request.getRequestDispatcher("/pages/email/send.jsp").forward(request, response);
        } catch (NullPointerException e) {
            // [FAILURE LOG] Log null pointer error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Send] NullPointerException: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro interno: falha ao acessar recurso.");
            request.setAttribute("errorUrl", "/pages/email/send.jsp");
            request.getRequestDispatcher("/pages/email/send.jsp").forward(request, response);
        } catch (ServletException e) {
            // [FAILURE LOG] Log servlet error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Send] ServletException: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro de processamento da requisição.");
            request.setAttribute("errorUrl", "/pages/email/send.jsp");
            request.getRequestDispatcher("/pages/email/send.jsp").forward(request, response);
        } catch (Exception e) {
            // [FAILURE LOG] Log generic error
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [Send] Exception: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro inesperado.");
            request.setAttribute("errorUrl", "/pages/email/send.jsp");
            request.getRequestDispatcher("/pages/email/send.jsp").forward(request, response);
        }
    }
}
