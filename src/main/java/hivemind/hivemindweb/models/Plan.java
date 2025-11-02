package hivemind.hivemindweb.models;

public class Plan {
    private int id;
    private String name;
    private String description;
    private int duration;
    private double price;
    private boolean isActive;

    public Plan(int id, String name, String description, int duration, double price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.isActive = isActive;
    }

    public Plan(String name, int duration, double price, boolean isActive) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.isActive = isActive;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }
}
