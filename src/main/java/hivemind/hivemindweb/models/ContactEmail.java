package hivemind.hivemindweb.models;

public class ContactEmail {
    // Variáveis
    private int id;
    private String email;
    private String company;

    // Construtor
    public ContactEmail(int id, String email, String company){
        this.id = id;
        this.email = email;
        this.company = company;
    }

    // ToString
    @Override
    public String toString() {
        return "=== Dados do Contato ===\n" +
               "Id    : " + id + "\n" +
               "Email : " + email + "\n" +
               "Empresa: " + company + "\n" +
               "========================";
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getCompany() {
        return company;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
