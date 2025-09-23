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
    public String toString(){
        return "Id: "+this.id+"\nEmail: "+this.email+"\nCompany: "+this.company;
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
