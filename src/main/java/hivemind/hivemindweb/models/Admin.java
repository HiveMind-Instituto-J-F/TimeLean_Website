package hivemind.hivemindweb.models;
public class Admin{
    // Variáveis
    private int id;
    private String email;
    private String hashPassword;

    // Construtor
    public Admin(int id, String email, String hashPassword){
        this.id = id;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    // Construtor
    public Admin(String email, String hashPassword){
        this.email = email;
        this.hashPassword = hashPassword;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getHashPassword() {
        return hashPassword;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    // ToString
    @Override
    public String toString() {
        return "=== Dados do Admin ===\n" +
               "Id          : " + id + "\n" +
               "Email       : " + email + "\n" +
               "HashPassword: " + hashPassword + "\n" +
               "=======================";
    }
}