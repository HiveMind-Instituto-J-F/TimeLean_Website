package hivemind.hivemindweb.models;

public class Worker {
    // Declare attributes
    private String cpf;
    private String role;
    private String sector;
    private String name;
    private String loginEmail;
    private String loginPassword;
    private String cnpjPlant;

    // Constructor Methods
    public Worker(String cpf) {this.cpf = cpf;};
    public Worker(String cpf, String role, String sector, String name,
                  String loginEmail, String loginPassword, String cnpjPlant) {
        this.cpf = cpf;
        this.role = role;
        this.sector = sector;
        this.name = name;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.cnpjPlant = cnpjPlant;
    }
    // Getters:
    public String getCpf() {
        return cpf;
    }
    public String getRole() {
        return role;
    }
    public String getSector() {
        return sector;
    }
    public String getName() {
        return name;
    }
    public String getLoginEmail() {
        return loginEmail;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public String getCnpjPlant() {
        return cnpjPlant;
    }

    // Setters:
    public void setRole(String role) {
        this.role = role;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    // Method toString()
    @Override
    public String toString() {
        return "Worker{" +
                "cpf='" + cpf + '\'' +
                ", role='" + role + '\'' +
                ", sector='" + sector + '\'' +
                ", name='" + name + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", cnpjPlant='" + cnpjPlant + '\'' +
                '}';
    }
}