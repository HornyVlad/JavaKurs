public class Data implements java.io.Serializable{
    private String name;
    private int points;

    public Data(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}