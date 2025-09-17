package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Worker {
    // Variáveis
    private String CPF;
    private String email;
    private String name;
    private String lastName;
    private String loginPassword;
    private String sector;
    private String role;

    // Construtor
    public Worker(String CPF, String email, String name, String lastName, String loginPassword, String sector, String role){
        this.CPF = CPF;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.loginPassword = loginPassword;
        this.sector = sector;
        this.role = role;
    }


    // Getters e Setters
    public String getCPF() {
        return CPF;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public String getSector() {
        return sector;
    }
    public String getRole() {
        return role;
    }

    // ToString
    public String toString(){
        return "\nCPF: "+this.CPF+"\nEmail: "+this.email+"\nName: "+this.name+"\nLast Name: "+this.lastName+"\nLogin Password: "
                +this.loginPassword+"\nSector: "+this.sector+"\nRole: "+this.role;
    }

}
