package hivemind.hivemindweb.models;

public class Admin {
    private int id;
    private String email;
    private String password;
    private String image;

    public Admin(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage(){
        return image;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String hashPassword) {
        this.password = hashPassword;
    }

    public void setImage(String image){
        this.image = image;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", hashPassword='" + password + '\'' +
                '}';
    }
}
