package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Plant {
    // Variáveis
    private String CNAE;
    private String CNPJ;
    private String location;
    private String responsible;
    private String operationalStatus;
    private String state;
    private String city;
    private String street;
    private String country;
    private String adressType;
    private int number;
    private String CEP;

    // Construtor
    public Plant(String CNAE, String CNPJ, String location, String responsible, String operationalStatus, String state, String city, String street,
                 String country, String adressType, int number, String CEP){
        this.CNAE = CNAE;
        this.CNPJ = CNPJ;
        this.location = location;
        this.responsible = responsible;
        this.operationalStatus = operationalStatus;
        this.state = state;
        this.city = city;
        this.street = street;
        this.country = country;
        this.adressType = adressType;
        this.number = number;
        this.CEP = CEP;
    }

    // ToString
    public String toString(){
        return "CNAE: "+this.CNAE+"\nCNPJ: "+this.CNPJ+"\nLocation: "+this.location+"\nResponsible: "+this.responsible+"\nOperational Status: "
                +this.operationalStatus+"\nCountry: "+this.country+"\nState: "+this.state+"\nCity: "+this.city+"\nStreet: "+this.street
                +"\nNumber: "+this.number+"\nCEP: "+this.CEP;
    }

    // Getters e Setters
    public String getCNAE() {
        return CNAE;
    }
    public String getCNPJ() {
        return CNPJ;
    }
    public String getLocation() {
        return location;
    }
    public String getResponsible() {
        return responsible;
    }
    public String getOperationalStatus() {
        return operationalStatus;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public String getCountry() {
        return country;
    }
    public String getAdressType() {
        return adressType;
    }
    public int getNumber() {
        return number;
    }
    public String getCEP() {
        return CEP;
    }
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }
    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setAdressType(String adressType) {
        this.adressType = adressType;
    }

    // Field
    public static void printFields(Field[] field) {
        field = Plant.class.getDeclaredFields();
        for (Field f : field) {
            System.out.println("Atributo: " + f.getName() + " | Tipo: " + f.getType());
        }
    }
}
