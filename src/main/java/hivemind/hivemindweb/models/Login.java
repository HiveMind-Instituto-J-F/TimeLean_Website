package hivemind.hivemindweb.models;

public class Login {
    // Variáveis
    private int id;
    private String email;
    private String password;

    // Construtor
    public Login(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    // ToString
    public String toString(){
        return "ID: "+this.id+"\nEmail: "+this.email+"\nPassword: "+this.password;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
