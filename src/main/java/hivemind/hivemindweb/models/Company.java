package hivemind.hivemindweb.models;

import java.lang.reflect.Field;

public class Company {
    // Variáveis
    private String cnpj;
    private String name;
    private String cnae;
    private String registrantCpf;



    // Construtor
    public Company(String cnpj, String name, String cnae, String registrantCpf){
        this.cnpj = cnpj;
        this.name = name;
        this.cnae = cnae;
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
    public String getRegistrantCpf() {
        return registrantCpf;
    }
}
