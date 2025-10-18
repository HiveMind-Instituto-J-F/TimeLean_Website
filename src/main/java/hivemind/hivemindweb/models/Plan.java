<<<<<<< HEAD
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

=======
package hivemind.hivemindweb.models;

public class Plan {
    // Variáveis
    private int id;
    private String name;
    private String description;
    private int duration;
    private double price;
    private boolean is_active;

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

    public Plan(int id, boolean is_active){
        this.id = id;
        this.is_active = is_active;
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
    public boolean getActive(){
        return is_active;
    }
    public void setActive(boolean is_active){
        this.is_active = is_active;
    }
}

>>>>>>> bec76c863b8b381cd6913140d14f7aa707f69381
