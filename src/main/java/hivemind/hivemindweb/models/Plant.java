package hivemind.hivemindweb.models;


public class Plant {
    // Variáveis
    private String cnae;
    private String cnpj;
    private String responsibleCpf;
    private boolean operationalStatus;
    private int adressNumber;
    private String cnpjCompany;
    private String adressCep;

    // Construtor
    public Plant(String cnpj, String cnae, String responsibleCpf, boolean operationalStatus, String addressCep,
                 int addressNumber, String cnpjCompany) {
        this.cnae = cnae;
        this.cnpj = cnpj;
        this.responsibleCpf = responsibleCpf;
        this.operationalStatus = operationalStatus;
        this.adressNumber = adressNumber;
        this.cnpjCompany = cnpjCompany;
        this.adressCep = adressCep;
    }

    // ToString
    public String toString(){
        return "CNAE: "+this.cnae+"\nCNPJ: "+this.cnpj+"\nResponsible CPF: "+this.responsibleCpf+"\nOperational Status: "
                +this.operationalStatus +"\nAdress Number: "+this.adressNumber+"\nAdress CEP: "+this.adressCep+"\nCNPJ Company: "
                +this.cnpjCompany;
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
    public boolean getOperationalStatus() {
        return operationalStatus;
    }
    public int getAdressNumber() {
        return adressNumber;
    }
    public String getIdCompany() {
        return cnpjCompany;
    }
    public String getAdressCep() {
        return adressCep;
    }
    public void setResponsibleCpf(String responsible) {
        this.responsibleCpf = responsible;
    }
    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }
    public void setAdressCep(String adressCep) {
        this.adressCep = adressCep;
    }
    public void setAdressNumber(int adressNumber) {
        this.adressNumber = adressNumber;
    }

}
