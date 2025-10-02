package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class PlanSubscription {
    // Variáveis
    private int id;
    private LocalDate startDate;
    private String cnpjCompany;
    private int idPlan;

    // Construtor
    public PlanSubscription(int id, LocalDate startDate, String cnpjCompany, int idPlan){
        this.id = id;
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
    }
    public PlanSubscription(LocalDate startDate, String cnpjCompany, int idPlan){
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
    }

    // ToString
    public String toString(){
        return "Id: "+this.id+"\nStart Date: "+this.startDate+"\nCNPJ Company: "+this.cnpjCompany+
                "\nId Plan: "+this.idPlan;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public String getCnpjCompany() {
        return cnpjCompany;
    }
    public int getIdPlan(){
        return idPlan;
    }
}
