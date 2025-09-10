package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Worker {
    // Variáveis
    private String CPF;
    private long id;
    private String name;
    private String lastName;
    private String password;
    private String sector;
    private String profileType;

    // Construtor
    public Worker(String CPF, String name, String lastName, String password, String sector, String profileType){
        this.CPF = CPF;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.sector = sector;
        this.profileType = profileType;
    }

    public Worker(int id) { // For Teste In TDD
        this.id = id;
    }

    // Getters e Setters
    public long getId(){return this.id;}
    public String getCPF() {
        return CPF;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getSector() {
        return sector;
    }
    public String getProfileType() {
        return profileType;
    }

    // ToString
    public String toString(){
        return "\nID:"+this.id  + "\nCPF: "+this.CPF+"\nName: "+this.name+"\nLast Name: "+this.lastName+"\nPassword: "
                +this.password+"\nSector: "+this.sector+"\nProfile Type: "+this.profileType;
    }
}
