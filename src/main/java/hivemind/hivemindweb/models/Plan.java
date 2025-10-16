package hivemind.hivemindweb.models;

public class Plan {
    // Variáveis
    private int id;
    private String name;
    private String description;
    private int duration;
    private double price;

    // Construtor
    public Plan(int id, String name, String description, int duration, double price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    // Construtor
    public Plan(String name, String description, int duration, double price){
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    public Plan(int id, String name){
        this.id = id;
        this.name = name;
    }

    // ToString
    public String toString(){
        return "ID: "+this.id+"\nName: "+this.name+"\nDescription: "+this.description+
                "\nDuration: "+this.duration+ ("\nPrice: %.2f," +  this.price);
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

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setValue(double value) {
        this.price = value;
    }
}

