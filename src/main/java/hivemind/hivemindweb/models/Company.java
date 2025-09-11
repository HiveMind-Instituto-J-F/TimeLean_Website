package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Company {
    // Variáveis
    private String CNPJ;
    private String companyName;
    private String companyType;
    private String registrantName;
    private String registrantLastName;
    private String registrantEmail;
    private String function;
    private String password;
    private int id;
    private String CPF;
    private Field[] field;

    // Construtor
    public Company(String CNPJ, String companyName, String companyType, String registrantName, String registrantLastName,
                   String registrantEmail, String function, String password, String CPF , int id){
        this.CNPJ = CNPJ;
        this.id = id;
        this.companyName = companyName;
        this.companyType = companyType;
        this.registrantName = registrantName;
        this.registrantLastName = registrantLastName;
        this.registrantEmail = registrantEmail;
        this.function = function;
        this.password = password;
        this.CPF = CPF;
    }

    public Company(String companyName, String companyType, String registrantName, String registrantLastName, String registrantEmail, String function, int id) {
        this.companyName = companyName;
        this.companyType = companyType;
        this.registrantName = registrantName;
        this.registrantLastName = registrantLastName;
        this.registrantEmail = registrantEmail;
        this.function = function;
        this.id = id;
    }

    // ToString
    public String toString(){
        return "Company Name: "+this.companyName+"\nCNPJ: "+this.CNPJ+"\nCompany Type: "+this.companyType+"\nRegistrant name: "+this.registrantName
                +"\nRegistrant Last Name: "+this.registrantLastName+"\nRegistrant Email: "+this.registrantEmail+"\nFunction: "+this.function
                +"\nPassword: "+this.password+"\nCPF: "+this.CPF;
    }

    // Getters e Setters
    public String getCNPJ() {
        return CNPJ;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getCompanyType() {
        return companyType;
    }
    public String getRegistrantName() {
        return registrantName;
    }
    public String getRegistrantLastName() {
        return registrantLastName;
    }
    public String getRegistrantEmail() {
        return registrantEmail;
    }
    public String getFunction() {
        return function;
    }
    public String getPassword() {
        return password;
    }
    public String getCPF() {
        return CPF;
    }
    public int getId(){return id;}
    public void setFunction(String function) {
        this.function = function;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Field
    public static void printFields(Field[] field) {
        field = Company.class.getDeclaredFields();
        for (Field f : field) {
            System.out.println("Atributo: " + f.getName() + " | Tipo: " + f.getType());
        }
    }
}
