package hivemind.hivemindweb.models;


public class Plant {
    // Variáveis
    private String cnae;
    private String cnpj;
    private String responsibleCpf;
    private boolean operationalStatus;
    private int addressNumber;
    private String cnpjCompany;
    private String addressCep;

    // Construtor
    public Plant(String cnpj, String cnae, String responsibleCpf, boolean operationalStatus, String addressCep,
                 int addressNumber, String cnpjCompany) {
        this.cnae = cnae;
        this.cnpj = cnpj;
        this.responsibleCpf = responsibleCpf;
        this.operationalStatus = operationalStatus;
        this.addressNumber = addressNumber;
        this.cnpjCompany = cnpjCompany;
        this.addressCep = addressCep;
    }

    public Plant(String cnpj) {
        this.cnpj = cnpj;
    }

    // ToString
    public String toString(){
        return "CNAE: "+this.cnae+"\nCNPJ: "+this.cnpj+"\nResponsible CPF: "+this.responsibleCpf+"\nOperational Status: "
                +this.operationalStatus +"\nAdress Number: "+this.addressNumber+"\nAdress CEP: "+this.addressCep+"\nCNPJ Company: "
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
        return addressNumber;
    }
    public String getCnpjCompany() {
        return cnpjCompany;
    }
    public String getAdressCep() {
        return addressCep;
    }
    public void setResponsibleCpf(String responsible) {
        this.responsibleCpf = responsible;
    }
    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }
    public void setAdressCep(String adressCep) {
        this.addressCep = adressCep;
    }
    public void setAdressNumber(int adressNumber) {
        this.addressNumber = adressNumber;
    }

}
