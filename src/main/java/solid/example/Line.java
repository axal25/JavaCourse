package solid.example;

public class Line implements Shape {

    private final double length;

    public Line(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }
}
