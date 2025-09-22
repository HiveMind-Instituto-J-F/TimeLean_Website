package hivemind.hivemindweb.models;

public class Plan {
    // Variáveis
    private int id;
    private String name;
    private String description;
    private int duration;
    private  double price;
    private int reportsLimit;
    private int plantsLimit;

    // Construtor
    public Plan(int id, String name, String description, int duration, double price, int plantsLimit, int reportsLimit){
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.plantsLimit = plantsLimit;
        this.reportsLimit = reportsLimit;
    }

    // ToString
    public String toString(){
        return "ID: "+this.id+"\nName: "+this.name+"\nDescription: "+this.description+
                "\nDuration: "+this.duration+ ("\nPrice: %.2f," +  this.price)+ "\nPlants Limit: "+this.plantsLimit+
                "\nReports Limit: "+this.reportsLimit;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public int getPlantsLimit() {
        return plantsLimit;
    }

    public int getReportsLimit() {
        return reportsLimit;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setValue(double value) {
        this.price = value;
    }
    public void setPlantsLimit(int plantsLimit) {
        this.plantsLimit = plantsLimit;
    }
    public void setReportsLimit(int reportsLimit) {
        this.reportsLimit = reportsLimit;
    }
}

