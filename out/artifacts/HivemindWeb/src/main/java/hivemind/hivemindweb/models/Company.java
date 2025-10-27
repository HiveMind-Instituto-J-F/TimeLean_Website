package hivemind.hivemindweb.models;

public class Company {
    // Variáveis
    private final String cnpj;
    private String name;
    private String cnae;
    private final String registrantCpf;
    private boolean isActive;

    // Construtor
        // Define all values
    public Company(String cnpj, String name, String cnae, String registrantCpf, boolean isActive){
        this.cnpj = cnpj;
        this.name = name;
        this.cnae = cnae;
        this.registrantCpf = registrantCpf;
        this.isActive = isActive;
    }
        // Define minimium values
    public Company(String cnpj, String name, String registrantCpf){
        this.cnpj = cnpj;
        this.name = name;
        this.registrantCpf = registrantCpf;
    }

    // ToString
    public String toString(){
        return "CNPJ: "+this.cnpj+"\nName: "+this.name+"\nCNAE: "+this.cnae
                +"\nRegistrant CPF: "+this.registrantCpf;
    }

    // Getters e Setters
    public String getCNPJ() {
        return cnpj;
    }
    public String getName() {
        return name;
    }
    public String getCnae() {
        return cnae;
    }
    public boolean isActive(){ return isActive;}
    public String getRegistrantCpf() {
        return registrantCpf;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
