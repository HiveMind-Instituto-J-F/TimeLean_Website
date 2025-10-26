package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class PlanSubscription {
    // Variables
    private int id;
    private LocalDate startDate;
    private String cnpjCompany;
    private int idPlan;
    private int numberInstallments;
    private boolean status;

    // Constructor - all values
    public PlanSubscription(int id, LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments, boolean status){
        this.id = id;
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
        this.status = status;
    }

    // Constructor - minimum values
    public PlanSubscription(LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments, boolean status){
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getCnpjCompany() {
        return cnpjCompany;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public int getNumberInstallments() {
        return numberInstallments;
    }

    public boolean getStatus() {
        return status;
    }

    // Setters (only for attributes that can be modified)
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // String representation of the object
    @Override
    public String toString(){
        return String.format(
                "Id: %d\nStart Date: %s\nCNPJ Company: %s\nId Plan: %d\nNumber Installments: %d\nStatus: %b",
                this.id,
                this.startDate,
                this.cnpjCompany,
                this.idPlan,
                this.numberInstallments,
                this.status
        );
    }
}
