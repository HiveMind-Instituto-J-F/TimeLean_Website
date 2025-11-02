package hivemind.hivemindweb.models;

public class Plant {
    private String cnae;
    private final String cnpj;
    private final String responsibleCpf;
    private boolean operationalStatus;
    private int addressNumber;
    private final String cnpjCompany;
    private String addressCep;

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

    public Plant(String cnpj, String responsibleCpf, boolean operationalStatus, String cnpjCompany) {
        this.cnpj = cnpj;
        this.responsibleCpf = responsibleCpf;
        this.operationalStatus = operationalStatus;
        this.cnpjCompany = cnpjCompany;
    }

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

    @Override
    public String toString() {
        return "Plant{" +
                "cnae='" + cnae + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", responsibleCpf='" + responsibleCpf + '\'' +
                ", operationalStatus=" + operationalStatus +
                ", addressNumber=" + addressNumber +
                ", cnpjCompany='" + cnpjCompany + '\'' +
                ", addressCep='" + addressCep + '\'' +
                '}';
    }
}
