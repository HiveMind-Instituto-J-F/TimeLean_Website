package hivemind.hivemindweb.models;

public class Plant {
    // Variables
    private String cnae;
    private String cnpj;
    private String responsibleCpf;
    private boolean operationalStatus;
    private int addressNumber;
    private String cnpjCompany;
    private String addressCep;

    // Constructor - all values
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

    // Constructor - minimum values
    public Plant(String cnpj, String responsibleCpf, boolean operationalStatus, String cnpjCompany) {
        this.cnpj = cnpj;
        this.responsibleCpf = responsibleCpf;
        this.operationalStatus = operationalStatus;
        this.cnpjCompany = cnpjCompany;
    }

    // Getters
    public String getCnae() {
        return cnae;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getResponsibleCpf() {
        return responsibleCpf;
    }

    public boolean getOperationalStatus() {
        return operationalStatus;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public String getCnpjCompany() {
        return cnpjCompany;
    }

    public String getAddressCep() {
        return addressCep;
    }

    // Setters (only for attributes that can be modified)
    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    public void setAddressCep(String addressCep) {
        this.addressCep = addressCep;
    }

    // String representation of the object
    @Override
    public String toString() {
        return String.format(
                "CNAE: %s\nCNPJ: %s\nResponsible CPF: %s\nOperational Status: %b\nAddress Number: %d\nAddress CEP: %s\nCNPJ Company: %s",
                this.cnae,
                this.cnpj,
                this.responsibleCpf,
                this.operationalStatus,
                this.addressNumber,
                this.addressCep,
                this.cnpjCompany
        );
    }
}
