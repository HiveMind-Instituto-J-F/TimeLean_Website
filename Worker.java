public class Worker {
    // Variáveis
    private long CPF;
    private String name;
    private String lastName;
    private String password;
    private String sector;
    private String profileType;

    // Construtor
    public Worker(long CPF, String name, String lastName, String password, String sector, String profileType){
        this.CPF = CPF;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.sector = sector;
        this.profileType = profileType;
    }

    // ToString
    public String toString(){
        return "CPF: "+this.CPF+"\nName: "+this.name+"\nLast Name: "+this.lastName+"\nPassword: "
                +this.password+"\nSector: "+this.sector+"\nProfile Type: "+this.profileType;
    }
    // Getters e Setters
    public long getCPF() {
        return CPF;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getSector() {
        return sector;
    }
    public String getProfileType() {
        return profileType;
    }
}
