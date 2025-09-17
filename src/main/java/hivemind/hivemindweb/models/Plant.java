package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Plant {
    // Variáveis
    private String cnae;
    private String cnpj;
    private String responsibleCpf;
    private String operationalStatus;
    private String adressState;
    private String adressCity;
    private String adressStreet;
    private String adressCountry;
    private int adressNumber;
    private String idCompany;
    private String adressCep;

    // Construtor
    public Plant(String cnae, String cnpj, String responsibleCpf, String operationalStatus, String adressState, String adressCity, String adressStreet,
                 String adressCountry, int adressNumber, String idCompany, String adressCep){
            this.cnae = cnae;
            this.cnpj = cnpj;
            this.responsibleCpf = responsibleCpf;
            this.operationalStatus = operationalStatus;
            this.adressState = adressState;
            this.adressCity = adressCity;
            this.adressStreet = adressStreet;
            this.adressCountry = adressCountry;
            this.adressNumber = adressNumber;
            this.idCompany = idCompany;
            this.adressCep = adressCep;
    }

    // ToString
    public String toString(){
        return "CNAE: "+this.cnae+"\nCNPJ: "+this.cnpj+"\nResponsible CPF: "+this.responsibleCpf+"\nOperational Status: "
                +this.operationalStatus+"\nAdress Country: "+this.adressCountry+"\nAdress State: "+this.adressState+"\nAdress City: "+this.adressCity+"\nAdress Street: "+this.adressStreet
                +"\nAdress Number: "+this.adressNumber+"\nAdress CEP: "+this.adressCep+"\nId Company: "+this.idCompany;
    }

    // Getters e Setters
    public String getCNAE() {
        return cnae;
    }
    public String getCNPJ() {
        return cnpj;
    }
    public String getResponsibleCpf() {
        return responsibleCpf;
    }
    public String getOperationalStatus() {
        return operationalStatus;
    }
    public String getAdressState() {
        return adressState;
    }
    public String getAdressCity() {
        return adressCity;
    }
    public String getAdressStreet() {
        return adressStreet;
    }
    public String getAdressCountry() {
        return adressCountry;
    }
    public int getAdressNumber() {
        return adressNumber;
    }
    public String getIdCompany() {
        return idCompany;
    }
    public String getAdressCep() {
        return adressCep;
    }
    public void setResponsibleCpf(String responsible) {
        this.responsibleCpf = responsible;
    }
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }
    public void setAdressCity(String adressCity) {
        this.adressCity = adressCity;
    }
    public void setAdressCep(String adressCep) {
        this.adressCep = adressCep;
    }
    public void setAdressCountry(String adressCountry) {
        this.adressCountry = adressCountry;
    }
    public void setAdressNumber(int adressNumber) {
        this.adressNumber = adressNumber;
    }
    public void setAdressState(String adressState) {
        this.adressState = adressState;
    }
    public void setAdressStreet(String adressStreet) {
        this.adressStreet = adressStreet;
    }
}
