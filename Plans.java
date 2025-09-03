public class Plans {
    // Variáveis
    private int id;
    private String name;
    private String description;
    private int qntSales;
    private int duration;
    private  double value;

    // Construtor
    public Plans(int id, String name, String description, int qntSales, int duration, double value){
        this.id = id;
        this.name = name;
        this.description = description;
        this.qntSales = qntSales;
        this.duration = duration;
        this.value = value;
    }

    // ToString
    public String toString(){
        return "ID: "+this.id+"\nName: "+this.name+"\nDescription: "+this.description+"\nQuantity of Sales: "+qntSales
                +"\nDuration: "+this.duration+ format("Value: %.2f", this.value);
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
    public int getQntSales() {
        return qntSales;
    }
    public int getDuration() {
        return duration;
    }
    public double getValue() {
        return value;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQntSales(int qntSales) {
        this.qntSales = qntSales;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setValue(double value) {
        this.value = value;
    }
}

