package hivemind.hivemindweb.models;

public class Company {
    // Variáveis
    private String cnpj;
    private String name;
    private String cnae;
    private String registrantCpf;
    private boolean isActive;

    // Construtor
    public Company(String cnpj, String name, String cnae, String registrantCpf, boolean isActive){
        this.cnpj = cnpj;
        this.name = name;
        this.cnae = cnae;
        this.registrantCpf = registrantCpf;
        this.isActive = isActive;
    }
    public Company(String cnpj){
        this.cnpj = cnpj;
    }

    public Company(String name,String registrantCpf){
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
}
