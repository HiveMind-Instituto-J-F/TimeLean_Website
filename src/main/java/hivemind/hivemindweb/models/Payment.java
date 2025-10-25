package hivemind.hivemindweb.models;

import java.time.LocalDate;

public class Payment {
    // Variáveis
    private int id;
    private double value;
    private LocalDate deadline;
    private String method;
    private String beneficiary;
    private String status;
    private int idPlanSubscription;
    

    // Construtor
    public Payment(double value, LocalDate deadline, String method, String beneficiary, String status, int idPlanSubscription){
        this.value = value;
        this.deadline = deadline;
        this.method = method;
        this.beneficiary = beneficiary;
        this.status = status;
        this.idPlanSubscription = idPlanSubscription;
    }

    public Payment(int id,double value, LocalDate deadline, String method, String beneficiary, String status, int idPlanSubscription){
        this.id = id;
        this.value = value;
        this.deadline = deadline;
        this.method = method;
        this.beneficiary = beneficiary;
        this.status = status;
        this.idPlanSubscription = idPlanSubscription;
    }
    public Payment(int id, LocalDate deadline, String method, String beneficiary, String status){
        this.id = id;
        this.deadline = deadline;
        this.method = method;
        this.beneficiary = beneficiary;
        this.status = status;
    }
    
    public Payment(int idPlanSubscription){
        this.idPlanSubscription = idPlanSubscription;
    }

    // ToString
    @Override
    public String toString() {
        return "=== Dados do Pagamento ===\n" +
               "Id               : " + id + "\n" +
               "Valor            : " + value + "\n" +
               "Data de Vencimento: " + deadline + "\n" +
               "Método           : " + method + "\n" +
               "Beneficiário     : " + beneficiary + "\n" +
               "Status           : " + status + "\n" +
               "Id Assinatura    : " + idPlanSubscription + "\n" +
               "==========================";
    }

    // Getters e Setters
    public int getId(){
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
    public int getIdPlan() {
        return idPlanSubscription;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
