package hivemind.hivemindweb.models;

public class Worker {
    // Variables
    private String cpf;
    private String role;
    private String sector;
    private String name;
    private String loginEmail;
    private String loginPassword;
    private String cnpjPlant;

    // Constructor - all values
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

    // Constructor - minimum values
    public Worker(String cpf, String cnpjPlant, String sector, String name, String loginEmail, String loginPassword) {
        this.cpf = cpf;
        this.cnpjPlant = cnpjPlant;
        this.sector = sector;
        this.name = name;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
    }

    // Getters
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

    // Setters (only for editable attributes)
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

    // String representation of the object
    @Override
    public String toString() {
        return String.format(
                "CPF: %s\nRole: %s\nSector: %s\nName: %s\nLogin Email: %s\nLogin Password: %s\nCNPJ Plant: %s",
                this.cpf,
                this.role,
                this.sector,
                this.name,
                this.loginEmail,
                this.loginPassword,
                this.cnpjPlant
        );
    }
}
