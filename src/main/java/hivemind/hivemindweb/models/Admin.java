package hivemind.hivemindweb.models;

public class Admin {
    private int id;
    private String email;
    private String hashPassword;

    public Admin(int id, String email, String hashPassword) {
        this.id = id;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    public Admin(String email, String hashPassword) {
        this.email = email;
        this.hashPassword = hashPassword;
    }

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

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                '}';
    }
}
