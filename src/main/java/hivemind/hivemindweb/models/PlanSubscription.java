package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class PlanSubscription {
    private int id;
    private LocalDate startDate;
    private final String cnpjCompany;
    private final int idPlan;
    private final int numberInstallments;
    private boolean status;

    public PlanSubscription(int id, LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments, boolean status) {
        this.id = id;
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
        this.status = status;
    }

    public PlanSubscription(LocalDate startDate, String cnpjCompany, int idPlan, int numberInstallments, boolean status) {
        this.startDate = startDate;
        this.cnpjCompany = cnpjCompany;
        this.idPlan = idPlan;
        this.numberInstallments = numberInstallments;
        this.status = status;
    }

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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "PlanSubscription{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", cnpjCompany='" + cnpjCompany + '\'' +
                ", idPlan=" + idPlan +
                ", numberInstallments=" + numberInstallments +
                ", status=" + status +
                '}';
    }
}
