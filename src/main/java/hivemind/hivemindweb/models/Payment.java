package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class Payment {
    private int id;
    private double value;
    private LocalDate deadline;
    private String method;
    private String beneficiary;
    private String status;
    private final int idPlanSubscription;

    public Payment(int id, double value, LocalDate deadline, String method, String beneficiary, String status, int idPlanSubscription) {
        this.id = id;
        this.value = value;
        this.deadline = deadline;
        this.method = method;
        this.beneficiary = beneficiary;
        this.status = status;
        this.idPlanSubscription = idPlanSubscription;
    }

    public Payment(double value, LocalDate deadline, String method, String beneficiary, String status, int idPlanSubscription) {
        this.value = value;
        this.deadline = deadline;
        this.method = method;
        this.beneficiary = beneficiary;
        this.status = status;
        this.idPlanSubscription = idPlanSubscription;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getMethod() {
        return method;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public String getStatus() {
        return status;
    }

    public int getIdPlanSubscription() {
        return idPlanSubscription;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", value=" + value +
                ", deadline=" + deadline +
                ", method='" + method + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", status='" + status + '\'' +
                ", idPlanSubscription=" + idPlanSubscription +
                '}';
    }
}
