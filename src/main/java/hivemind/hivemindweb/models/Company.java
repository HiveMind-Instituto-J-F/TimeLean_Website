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

    public Company(String cnpj, boolean isActive){
        this.cnpj = cnpj;
        this.isActive = isActive;
    }

    public Company(String cnpj, String name, String cnae, String registrantCpf){
        this.cnpj = cnpj;
        this.name = name;
        this.cnae = cnae;
        this.registrantCpf = registrantCpf;
    }

    public Company(){
    }

    // ToString
    @Override
    public String toString() {
        return "=== Dados da Empresa ===\n" +
               "CNPJ         : " + cnpj + "\n" +
               "Nome         : " + name + "\n" +
               "CNAE         : " + cnae + "\n" +
               "CPF Registrante: " + registrantCpf + "\n" +
               "Ativo        : " + isActive + "\n" +
               "========================";
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
