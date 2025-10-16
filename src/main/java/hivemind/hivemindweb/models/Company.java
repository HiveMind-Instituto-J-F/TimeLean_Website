package hivemind.hivemindweb.models;

public class Company {
    // Variáveis
    private String cnpj;
    private String name;
    private String cnae;
    private String registrantCpf;
    private boolean is_active;

    // Construtor
    public Company(String cnpj, String name, String cnae, String registrantCpf){
        this.cnpj = cnpj;
        this.name = name;
        this.cnae = cnae;
        this.registrantCpf = registrantCpf;
    }
    public Company(String cnpj){
        this.cnpj = cnpj;
    }

    public Company(String name,String registrantCpf){
        this.name = name;
        this.registrantCpf = registrantCpf;
    }

    public Company(String cnpj, boolean is_active){
        this.cnpj = cnpj;
        this.is_active = is_active;
    }
    public Company(){
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
    public String getRegistrantCpf() {
        return registrantCpf;
    }
    public boolean getActive(){
        return is_active;
    }
    public void setActive(boolean is_active){
        this.is_active = is_active;
    }
}
