package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class PlanSubscription {
    // Variáveis
    private int id;
    private LocalDate startDate;
    private String cnpjCompany;
    private int idPlan;
    private int numberInstallments;
    private boolean status;

    // Construtor
    public PlanSubscription(int id, LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments, boolean status){
        this.id = id;
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
        this.status = status;
    }
    public PlanSubscription(LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments){
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
    }

    // Builder method --> update
    public PlanSubscription(int id, LocalDate startDate, boolean status) {this.id = id; this.startDate = startDate; this.status=status;}
    public PlanSubscription(int id){
        this.id = id;
    }

    // ToString
    public String toString(){
    return "Id: "+this.id+"\nStart Date: "+this.startDate+"\nCNPJ Company: "+this.cnpjCompany+
        "\nId Plan: "+this.idPlan+"\nNumber Installments: "+this.numberInstallments;
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
    public boolean getStatus(){return status;}
    public int getNumberInstallments(){
        return numberInstallments;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
