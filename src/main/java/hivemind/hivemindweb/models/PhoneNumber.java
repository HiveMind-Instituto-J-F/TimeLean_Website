package hivemind.hivemindweb.models;

public class PhoneNumber {
    // Variáveis
    private int id;
    private String phoneNumber;
    private String cpfWorker;

    // Construtor
    public PhoneNumber(int id, String phoneNumber, String cpfWorker){
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.cpfWorker = cpfWorker;
    }

    // ToString
    public String toString(){
        return "\nId: "+this.id+"\nPhone Number: "+this.phoneNumber+"\nCPF Worker: "+this.cpfWorker;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCpfWorker() {
        return cpfWorker;
    }
}
